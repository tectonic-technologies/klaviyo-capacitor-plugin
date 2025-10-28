// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "KlaviyoCapacitorPlugin",
    platforms: [.iOS(.v14)],
    products: [
        .library(
            name: "KlaviyoCapacitorPlugin",
            targets: ["TectonicKlaviyoPlugin"])
    ],
    dependencies: [
        .package(url: "https://github.com/ionic-team/capacitor-swift-pm.git", from: "7.0.0")
    ],
    targets: [
        .target(
            name: "TectonicKlaviyoPlugin",
            dependencies: [
                .product(name: "Capacitor", package: "capacitor-swift-pm"),
                .product(name: "Cordova", package: "capacitor-swift-pm")
            ],
            path: "ios/Sources/TectonicKlaviyoPlugin"),
        .testTarget(
            name: "TectonicKlaviyoPluginTests",
            dependencies: ["TectonicKlaviyoPlugin"],
            path: "ios/Tests/TectonicKlaviyoPluginTests")
    ]
)
