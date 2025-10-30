import Foundation
import KlaviyoSwift

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
}
