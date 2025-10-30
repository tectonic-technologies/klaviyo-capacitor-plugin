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
}
