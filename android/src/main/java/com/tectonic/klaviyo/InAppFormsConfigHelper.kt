package com.tectonic.klaviyo

import com.klaviyo.forms.InAppFormsConfig
import kotlin.time.Duration.Companion.seconds

/**
 * Kotlin Helper Function to Create InAppFormsConfig from Java Code
 * 
 * WHY THIS FILE EXISTS (Java-Kotlin Interoperability Issue):
 * 
 * The Klaviyo Android SDK's InAppFormsConfig class is written in Kotlin and has the following issues
 * when accessed from Java:
 * 
 * 1. **Private Constructor with Kotlin Duration**: The constructor that accepts a timeout duration
 *    uses Kotlin's `Duration` type (e.g., `30.minutes`), which cannot be easily created from Java.
 *    The Duration type requires Kotlin-specific functionality (extension functions like `.seconds`).
 * 
 * 2. **No Public Constructors**: All constructors for InAppFormsConfig are either private or
 *    require Kotlin-specific types that aren't easily accessible from Java.
 * 
 * 3. **SDK Design**: The Klaviyo SDK is Kotlin-first, and while Kotlin code is generally
 *    Java-interoperable, certain Kotlin features (like Duration with extension functions) don't
 *    translate well to Java.
 * 
 * WHY NOT USE ALTERNATIVES:
 * 
 * - **Reflection to Access Private Constructor**: Possible but fragile, breaks encapsulation,
 *   and may break with SDK updates. Not recommended.
 * 
 * - **Ignore Timeout Configuration**: We could skip timeout support, but this reduces functionality
 *   for users who need custom timeout settings.
 * 
 * - **Request SDK Change**: While ideal long-term, we need a solution now. The SDK would need to
 *   expose a public factory method or Java-friendly constructor.
 * 
 * SOLUTION:
 * 
 * This Kotlin helper function bridges the gap by:
 * - Accepting a simple Java type (Long for seconds)
 * - Converting it to Kotlin Duration using `.seconds` extension
 * - Creating the InAppFormsConfig instance with proper Kotlin types
 * - Being callable from Java as `InAppFormsConfigHelperKt.createInAppFormsConfig()`
 * 
 * NOTE: Mixing Java and Kotlin in the same project is common and well-supported in Android
 * development. The Klaviyo SDK itself is Kotlin-based, so we're already depending on Kotlin
 * indirectly. This small helper file adds minimal complexity while providing full functionality.
 * 
 * @param sessionTimeoutSeconds Optional timeout in seconds. If null or <= 0, uses default config.
 * @return InAppFormsConfig instance configured with the provided timeout or default settings.
 */
fun createInAppFormsConfig(sessionTimeoutSeconds: Long?): InAppFormsConfig {
    return if (sessionTimeoutSeconds != null && sessionTimeoutSeconds > 0) {
        InAppFormsConfig(sessionTimeoutDuration = sessionTimeoutSeconds.seconds)
    } else {
        InAppFormsConfig()
    }
}

