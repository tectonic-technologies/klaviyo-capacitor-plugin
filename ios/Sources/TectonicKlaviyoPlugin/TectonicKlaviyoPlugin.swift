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
        CAPPluginMethod(name: "setProfile", returnType: CAPPluginReturnPromise)
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
}
