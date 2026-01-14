import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(TectonicKlaviyoPlugin)
public class TectonicKlaviyoPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "TectonicKlaviyoPlugin"
    public let jsName = "TectonicKlaviyo"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "initialize", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setProfile", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setExternalId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getExternalId", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getEmail", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPhoneNumber", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setProfileAttribute", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "resetProfile", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setPushToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPushToken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "setBadgeCount", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createEvent", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "registerForInAppForms", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unregisterFromInAppForms", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "registerDeepLinkHandler", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "unregisterDeepLinkHandler", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "handleUniversalTrackingLink", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = TectonicKlaviyo()
    
    // Store the latest deep link that arrives before handler is registered
    private var pendingDeepLink: URL?
    private var isHandlerRegistered = false
    private var isKlaviyoSDKHandlerRegistered = false
    private let stateLock = NSLock()
    
    public override func load() {
        // Register Klaviyo SDK handler early to capture deep links even when app is killed
        // This ensures we don't miss deep links that arrive before JavaScript initializes
        implementation.registerDeepLinkHandler { [weak self] url in
            self?.handleKlaviyoDeepLink(url)
        }
        isKlaviyoSDKHandlerRegistered = true
    }
    
    private func handleKlaviyoDeepLink(_ url: URL) {
        stateLock.lock()
        if isHandlerRegistered {
            // Handler is registered, emit immediately
            let payload: [String: Any] = ["uri": url.absoluteString]
            stateLock.unlock()
            notifyListeners("klaviyoDeepLink", data: payload)
        } else {
            // Handler not registered yet, store the latest link
            pendingDeepLink = url
            stateLock.unlock()
        }
    }

    @objc func initialize(_ call: CAPPluginCall) {
        guard let apiKey = call.getString("apiKey"), !apiKey.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            call.reject("apiKey is required")
            return
        }
        implementation.initialize(apiKey: apiKey)
        call.resolve()
    }

    @objc func setProfile(_ call: CAPPluginCall) {
        let dict = call.options as? [String: Any] ?? [:]
        implementation.setProfile(dict)
        call.resolve()
    }

    @objc func setExternalId(_ call: CAPPluginCall) {
        guard let externalId = call.getString("externalId"), !externalId.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            call.reject("externalId is required")
            return
        }
        implementation.setExternalId(externalId)
        call.resolve()
    }

    @objc func getExternalId(_ call: CAPPluginCall) {
        call.resolve(["externalId": implementation.getExternalId() as Any])
    }

    @objc func setEmail(_ call: CAPPluginCall) {
        guard let email = call.getString("email"), !email.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            call.reject("email is required")
            return
        }
        implementation.setEmail(email)
        call.resolve()
    }

    @objc func getEmail(_ call: CAPPluginCall) {
        call.resolve(["email": implementation.getEmail() as Any])
    }

    @objc func setPhoneNumber(_ call: CAPPluginCall) {
        guard let phoneNumber = call.getString("phoneNumber"), !phoneNumber.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            call.reject("phoneNumber is required")
            return
        }
        implementation.setPhoneNumber(phoneNumber)
        call.resolve()
    }

    @objc func getPhoneNumber(_ call: CAPPluginCall) {
        call.resolve(["phoneNumber": implementation.getPhoneNumber() as Any])
    }

    @objc func setProfileAttribute(_ call: CAPPluginCall) {
        guard let propertyKey = call.getString("propertyKey"), !propertyKey.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            call.reject("propertyKey is required")
            return
        }
        // Accept primitive JSON-safe types only
        if let value: String = call.getString("value") {
            implementation.setProfileAttribute(propertyKey, value: value)
        } else if let number = call.getDouble("value") {
            implementation.setProfileAttribute(propertyKey, value: NSNumber(value: number))
        } else if let boolValue = call.getBool("value") {
            implementation.setProfileAttribute(propertyKey, value: boolValue)
        } else {
            // If type cannot be coerced, skip gracefully
            call.reject("value must be string, number, or boolean")
            return
        }
        call.resolve()
    }

    @objc func resetProfile(_ call: CAPPluginCall) {
        implementation.resetProfile()
        call.resolve()
    }

    @objc func setPushToken(_ call: CAPPluginCall) {
        guard let token = call.getString("token"), !token.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            call.reject("token is required")
            return
        }
        implementation.setPushToken(token)
        call.resolve()
    }

    @objc func getPushToken(_ call: CAPPluginCall) {
        call.resolve(["token": implementation.getPushToken() as Any])
    }

    @objc func setBadgeCount(_ call: CAPPluginCall) {
        guard let count = call.getInt("count") else {
            call.reject("count is required")
            return
        }
        implementation.setBadgeCount(count)
        call.resolve()
    }

    @objc func createEvent(_ call: CAPPluginCall) {
        let payload = call.options as? [String: Any] ?? [:]
        implementation.createEvent(payload)
        call.resolve()
    }

    @MainActor @objc func registerForInAppForms(_ call: CAPPluginCall) {
        let configuration = (call.getObject("configuration"))
        implementation.registerForInAppForms(configuration)
        call.resolve()
    }

    @MainActor @objc func unregisterFromInAppForms(_ call: CAPPluginCall) {
        implementation.unregisterFromInAppForms()
        call.resolve()
    }

    @objc func registerDeepLinkHandler(_ call: CAPPluginCall) {
        stateLock.lock()
        let queuedLink = pendingDeepLink
        pendingDeepLink = nil
        isHandlerRegistered = true
        stateLock.unlock()
        
        // Re-register with Klaviyo SDK if it was previously unregistered
        if !isKlaviyoSDKHandlerRegistered {
            implementation.registerDeepLinkHandler { [weak self] url in
                self?.handleKlaviyoDeepLink(url)
            }
            isKlaviyoSDKHandlerRegistered = true
        }
        
        // Process the latest deep link that arrived before JavaScript handler was registered
        if let url = queuedLink {
            let payload: [String: Any] = ["uri": url.absoluteString]
            notifyListeners("klaviyoDeepLink", data: payload)
        }
        
        call.resolve()
    }

    @objc func unregisterDeepLinkHandler(_ call: CAPPluginCall) {
        stateLock.lock()
        isHandlerRegistered = false
        pendingDeepLink = nil
        stateLock.unlock()
        
        // Unregister from Klaviyo SDK - deep links will be lost until re-registered
        implementation.unregisterDeepLinkHandler()
        isKlaviyoSDKHandlerRegistered = false
        call.resolve()
    }

    @objc func handleUniversalTrackingLink(_ call: CAPPluginCall) {
        guard let trackingLink = call.getString("trackingLink"),
              let url = URL(string: trackingLink) else {
            call.reject("trackingLink is required and must be a valid URL")
            return
        }
        
        let handled = implementation.handleUniversalTrackingLink(url)
        call.resolve(["handled": handled])
    }
}
