import Foundation

@objc public class TectonicKlaviyo: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
