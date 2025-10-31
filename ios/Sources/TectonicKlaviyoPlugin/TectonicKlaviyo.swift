import Foundation
import KlaviyoSwift
import KlaviyoForms

@objc public class TectonicKlaviyo: NSObject {
    @objc public func initialize(apiKey: String) {
        // Defensive: avoid empty keys
        guard !apiKey.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            NSLog("Klaviyo initialize called with empty apiKey")
            return
        }

        // SDK initialize (per docs)
        KlaviyoSDK().initialize(with: apiKey)
    }

    @objc public func setProfile(_ profile: [String: Any]) {
        // Ignore empty payloads to avoid unnecessary SDK calls
        if profile.isEmpty {
            NSLog("Klaviyo setProfile called with empty profile payload; skipping")
            return
        }

        // Extract identifiers and attributes
        let externalId = (profile["externalId"] as? String)?.trimmingCharacters(in: .whitespacesAndNewlines)
        let email = (profile["email"] as? String)?.trimmingCharacters(in: .whitespacesAndNewlines)
        let phone = (profile["phoneNumber"] as? String)?.trimmingCharacters(in: .whitespacesAndNewlines)

        let firstName = profile["firstName"] as? String
        let lastName = profile["lastName"] as? String
        let title = profile["title"] as? String
        let organization = profile["organization"] as? String
        let image = profile["image"] as? String

        var locationModel: Profile.Location? = nil
        if let location = profile["location"] as? [String: Any] {
            let address1 = location["address1"] as? String
            let address2 = location["address2"] as? String
            let city = location["city"] as? String
            let country = location["country"] as? String
            let latitude = location["latitude"] as? Double
            let longitude = location["longitude"] as? Double
            let region = location["region"] as? String
            let zip = location["zip"] as? String
            let timezone = location["timezone"] as? String
            locationModel = Profile.Location(address1: address1,
                                             address2: address2,
                                             city: city,
                                             country: country,
                                             latitude: latitude,
                                             longitude: longitude,
                                             region: region,
                                             zip: zip,
                                             timezone: timezone)
        }

        // Validate custom properties: only keep JSON-safe primitives
        var properties: [String: Any]? = nil
        if let rawProps = profile["properties"] as? [String: Any] {
            var sanitized: [String: Any] = [:]
            for (k, v) in rawProps {
                let key = k.trimmingCharacters(in: .whitespacesAndNewlines)
                if key.isEmpty { continue }
                switch v {
                case let s as String where !s.isEmpty:
                    sanitized[key] = s
                case let n as NSNumber:
                    sanitized[key] = n
                case let b as Bool:
                    sanitized[key] = b
                default:
                    // Skip unsupported types (arrays/dictionaries/optionals/etc.)
                    continue
                }
            }
            if !sanitized.isEmpty { properties = sanitized }
        }

        // Construct a Profile and set once per docs
        let profileModel = Profile(email: (email?.isEmpty == false ? email : nil),
                                   phoneNumber: (phone?.isEmpty == false ? phone : nil),
                                   externalId: (externalId?.isEmpty == false ? externalId : nil),
                                   firstName: (firstName?.isEmpty == false ? firstName : nil),
                                   lastName: (lastName?.isEmpty == false ? lastName : nil),
                                   organization: (organization?.isEmpty == false ? organization : nil),
                                   title: (title?.isEmpty == false ? title : nil),
                                   image: (image?.isEmpty == false ? image : nil),
                                   location: locationModel,
                                   properties: properties)

        // Final dispatch to SDK
        KlaviyoSDK().set(profile: profileModel)
    }

    @objc public func setExternalId(_ externalId: String) {
        let value = externalId.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !value.isEmpty else {
            NSLog("Klaviyo setExternalId called with empty value; skipping")
            return
        }
        KlaviyoSDK().set(externalId: value)
    }

    @objc public func getExternalId() -> String? {
        return KlaviyoSDK().externalId
    }

    @objc public func setEmail(_ email: String) {
        let value = email.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !value.isEmpty else {
            NSLog("Klaviyo setEmail called with empty value; skipping")
            return
        }
        KlaviyoSDK().set(email: value)
    }

    @objc public func getEmail() -> String? {
        return KlaviyoSDK().email
    }

    @objc public func setPhoneNumber(_ phoneNumber: String) {
        let value = phoneNumber.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !value.isEmpty else {
            NSLog("Klaviyo setPhoneNumber called with empty value; skipping")
            return
        }
        KlaviyoSDK().set(phoneNumber: value)
    }

    @objc public func getPhoneNumber() -> String? {
        return KlaviyoSDK().phoneNumber
    }

    @objc public func setProfileAttribute(_ propertyKey: String, value: Any) {
        let key = propertyKey.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !key.isEmpty else {
            NSLog("Klaviyo setProfileAttribute called with empty propertyKey; skipping")
            return
        }

        // Map common keys to Profile.ProfileKey
        let attributeKey: Profile.ProfileKey
        switch key {
        case "firstName", "first_name": attributeKey = .firstName
        case "lastName", "last_name": attributeKey = .lastName
        case "title": attributeKey = .title
        case "organization": attributeKey = .organization
        case "image": attributeKey = .image
        case "address1": attributeKey = .address1
        case "address2": attributeKey = .address2
        case "city": attributeKey = .city
        case "region": attributeKey = .region
        case "zip": attributeKey = .zip
        case "country": attributeKey = .country
        case "timezone": attributeKey = .custom(customKey: "timezone")
        case "latitude": attributeKey = .latitude
        case "longitude": attributeKey = .longitude
        default:
            attributeKey = .custom(customKey: key)
        }

        // Allow JSON-safe primitives only
        switch value {
        case let s as String:
            guard !s.isEmpty else { return }
            KlaviyoSDK().set(profileAttribute: attributeKey, value: s)
        case let n as NSNumber:
            KlaviyoSDK().set(profileAttribute: attributeKey, value: n)
        case let b as Bool:
            KlaviyoSDK().set(profileAttribute: attributeKey, value: b)
        default:
            // Unsupported type, skip
            return
        }
    }

    @objc public func resetProfile() {
        KlaviyoSDK().resetProfile()
    }

    @objc public func setPushToken(_ token: String) {
        let value = token.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !value.isEmpty else {
            NSLog("Klaviyo setPushToken called with empty token; skipping")
            return
        }
        KlaviyoSDK().set(pushToken: value)
    }

    @objc public func getPushToken() -> String? {
        return KlaviyoSDK().pushToken
    }

    @objc public func setBadgeCount(_ count: Int) {
        KlaviyoSDK().setBadgeCount(count)
    }

    @objc public func createEvent(_ event: [String: Any]) {
        guard let rawName = event["name"] as? String else {
            NSLog("Klaviyo createEvent called without name; skipping")
            return
        }
        let name = rawName.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !name.isEmpty else {
            NSLog("Klaviyo createEvent called with empty name; skipping")
            return
        }

        // Optional value and uniqueId
        var finalProperties: [String: Any] = [:]
        var valueDouble: Double? = nil
        if let props = event["properties"] as? [String: Any] {
            for (k, v) in props {
                let key = k.trimmingCharacters(in: .whitespacesAndNewlines)
                if key.isEmpty { continue }
                switch v {
                case let s as String where !s.isEmpty:
                    finalProperties[key] = s
                case let n as NSNumber:
                    finalProperties[key] = n
                case let b as Bool:
                    finalProperties[key] = b
                default:
                    continue
                }
            }
        }

        if let valueNum = event["value"] as? NSNumber {
            valueDouble = valueNum.doubleValue
        } else if let v = event["value"] as? Double {
            valueDouble = v
        }

        let uniqueId = (event["uniqueId"] as? String)?.trimmingCharacters(in: .whitespacesAndNewlines)
        let finalUniqueId = (uniqueId?.isEmpty == false) ? uniqueId : nil

        let eventModel = Event(name: .customEvent(name),
                               properties: finalProperties.isEmpty ? nil : finalProperties,
                               value: valueDouble,
                               uniqueId: finalUniqueId)
        KlaviyoSDK().create(event: eventModel)
    }

    @MainActor @objc public func registerForInAppForms(_ configuration: [String: Any]?) {
        if let config = configuration {
            var timeoutSeconds: TimeInterval?
            if let intVal = config["sessionTimeoutDuration"] as? Int {
                timeoutSeconds = TimeInterval(intVal)
            } else if let doubleVal = config["sessionTimeoutDuration"] as? Double {
                timeoutSeconds = TimeInterval(doubleVal)
            }

            if let timeout = timeoutSeconds {
                let formsConfig = InAppFormsConfig(sessionTimeoutDuration: timeout)
                KlaviyoSDK().registerForInAppForms(configuration: formsConfig)
                return
            }
        }
        KlaviyoSDK().registerForInAppForms()
    }

    @MainActor @objc public func unregisterFromInAppForms() {
        KlaviyoSDK().unregisterFromInAppForms()
    }

    // Swift-only method (not @objc) to register deep link handler with a closure
    public func registerDeepLinkHandler(handler: @escaping (URL) -> Void) {
        KlaviyoSDK().registerDeepLinkHandler { url in
            handler(url)
        }
    }

    @objc public func unregisterDeepLinkHandler() {
        KlaviyoSDK().unregisterDeepLinkHandler()
    }

    @objc public func handleUniversalTrackingLink(_ url: URL) -> Bool {
        return KlaviyoSDK().handleUniversalTrackingLink(url)
    }
}
