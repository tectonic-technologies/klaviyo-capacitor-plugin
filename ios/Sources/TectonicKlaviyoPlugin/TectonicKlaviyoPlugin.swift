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
        CAPPluginMethod(name: "resetProfile", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = TectonicKlaviyo()

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
}
