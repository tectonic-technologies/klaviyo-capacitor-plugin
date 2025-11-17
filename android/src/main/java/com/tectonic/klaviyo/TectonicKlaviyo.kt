package com.tectonic.klaviyo

import android.content.Context
import androidx.activity.ComponentActivity
import com.getcapacitor.JSObject
import com.getcapacitor.Logger
import com.klaviyo.analytics.Klaviyo
import com.klaviyo.analytics.model.Event
import com.klaviyo.analytics.model.EventKey
import com.klaviyo.analytics.model.ProfileKey
import com.klaviyo.forms.InAppFormsConfig
import com.klaviyo.forms.registerForInAppForms
import com.klaviyo.forms.unregisterFromInAppForms
import android.content.Intent
import android.net.Uri
import kotlin.time.Duration.Companion.seconds

class TectonicKlaviyo {

    private fun setAttributeIfPresent(klaviyo: Klaviyo, data: JSObject, key: String, profileKey: ProfileKey) {
        try {
            if (data.has(key)) {
                val value = data.getString(key)
                if (!value.isNullOrEmpty()) {
                    klaviyo.setProfileAttribute(profileKey, value)
                }
            }
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set profile attribute: $key, error: ${e.message}", e)
        }
    }

    private fun setIdentifierIfPresent(klaviyo: Klaviyo, data: JSObject, key: String, methodName: String) {
        try {
            if (data.has(key)) {
                val value = data.getString(key)
                if (!value.isNullOrEmpty()) {
                    when (methodName) {
                        "externalId" -> klaviyo.setExternalId(value)
                        "email" -> klaviyo.setEmail(value)
                        "phoneNumber" -> klaviyo.setPhoneNumber(value)
                    }
                }
            }
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set identifier: $key, error: ${e.message}", e)
        }
    }

    fun initialize(context: Context, apiKey: String) {
        if (context !is ComponentActivity) {
            throw IllegalArgumentException("Context must be a ComponentActivity")
        }
        Logger.debug("Klaviyo", "Initializing Klaviyo SDK")
        Klaviyo.initialize(apiKey, context)
        Logger.debug("Klaviyo", "Klaviyo SDK initialized successfully")
    }

    fun setProfile(profileData: JSObject) {
        try {
            Logger.debug("Klaviyo", "Setting profile data")

            // Set identifiers using helper method
            setIdentifierIfPresent(Klaviyo, profileData, "externalId", "externalId")
            setIdentifierIfPresent(Klaviyo, profileData, "email", "email")
            setIdentifierIfPresent(Klaviyo, profileData, "phoneNumber", "phoneNumber")
            
            // Set profile attributes using helper method
            setAttributeIfPresent(Klaviyo, profileData, "firstName", ProfileKey.FIRST_NAME)
            setAttributeIfPresent(Klaviyo, profileData, "lastName", ProfileKey.LAST_NAME)
            setAttributeIfPresent(Klaviyo, profileData, "title", ProfileKey.TITLE)
            setAttributeIfPresent(Klaviyo, profileData, "organization", ProfileKey.ORGANIZATION)
            setAttributeIfPresent(Klaviyo, profileData, "image", ProfileKey.IMAGE)
            
            // Set location if present
            try {
                if (profileData.has("location")) {
                    val location = profileData.getJSObject("location")
                    if (location != null) {
                        setLocationAttributes(Klaviyo, location)
                    }
                }
            } catch (e: Exception) {
                Logger.error("Klaviyo", "Failed to set location data, error: ${e.message}", e)
            }
            
            // Set custom properties
            try {
                if (profileData.has("properties")) {
                    val properties = profileData.getJSObject("properties")
                    if (properties != null) {
                        setCustomProperties(Klaviyo, properties)
                    }
                }
            } catch (e: Exception) {
                Logger.error("Klaviyo", "Failed to set custom properties, error: ${e.message}", e)
            }
            
            Logger.debug("Klaviyo", "Profile data set successfully")
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set profile data, error: ${e.message}", e)
        }
    }

    fun setExternalId(externalId: String) {
        try {
            Logger.debug("Klaviyo", "Setting external ID")
            if (externalId.isNotEmpty()) {
                Klaviyo.setExternalId(externalId)
                Logger.debug("Klaviyo", "External ID set successfully")
            }
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set external ID, error: ${e.message}", e)
        }
    }

    fun getExternalId(): String? {
        return try {
            Logger.debug("Klaviyo", "Getting external ID")
            Klaviyo.getExternalId()
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to get external ID, error: ${e.message}", e)
            null
        }
    }

    fun setEmail(email: String) {
        try {
            Logger.debug("Klaviyo", "Setting email")
            if (email.isNotEmpty()) {
                Klaviyo.setEmail(email)
                Logger.debug("Klaviyo", "Email set successfully")
            }
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set email, error: ${e.message}", e)
        }
    }

    fun getEmail(): String? {
        return try {
            Logger.debug("Klaviyo", "Getting email")
            Klaviyo.getEmail()
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to get email, error: ${e.message}", e)
            null
        }
    }

    fun setPhoneNumber(phoneNumber: String) {
        try {
            Logger.debug("Klaviyo", "Setting phone number")
            if (phoneNumber.isNotEmpty()) {
                Klaviyo.setPhoneNumber(phoneNumber)
                Logger.debug("Klaviyo", "Phone number set successfully")
            }
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set phone number, error: ${e.message}", e)
        }
    }

    fun getPhoneNumber(): String? {
        return try {
            Logger.debug("Klaviyo", "Getting phone number")
            Klaviyo.getPhoneNumber()
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to get phone number, error: ${e.message}", e)
            null
        }
    }

    private fun getProfileKey(propertyKey: String): ProfileKey {
        // Map standard attribute keys to ProfileKey constants
        return when (propertyKey) {
            "firstName", "first_name" -> ProfileKey.FIRST_NAME
            "lastName", "last_name" -> ProfileKey.LAST_NAME
            "title" -> ProfileKey.TITLE
            "organization" -> ProfileKey.ORGANIZATION
            "image" -> ProfileKey.IMAGE
            "address1" -> ProfileKey.ADDRESS1
            "address2" -> ProfileKey.ADDRESS2
            "city" -> ProfileKey.CITY
            "region" -> ProfileKey.REGION
            "zip" -> ProfileKey.ZIP
            "country" -> ProfileKey.COUNTRY
            "timezone" -> ProfileKey.TIMEZONE
            "latitude" -> ProfileKey.LATITUDE
            "longitude" -> ProfileKey.LONGITUDE
            else -> ProfileKey.CUSTOM(propertyKey)
        }
    }

    fun setProfileAttribute(propertyKey: String, value: String) {
        try {
            Logger.debug("Klaviyo", "Setting profile attribute: $propertyKey")
            
            if (propertyKey.isEmpty()) {
                Logger.error("Klaviyo", "Property key cannot be null or empty", IllegalArgumentException("Property key cannot be null or empty"))
                return
            }
            
            if (value.isEmpty()) {
                Logger.debug("Klaviyo", "Value is null or empty, skipping profile attribute: $propertyKey")
                return
            }
            
            // Get the appropriate ProfileKey (standard or custom)
            val key = getProfileKey(propertyKey)
            Klaviyo.setProfileAttribute(key, value)
            Logger.debug("Klaviyo", "Profile attribute set successfully: $propertyKey")
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set profile attribute: $propertyKey, error: ${e.message}", e)
        }
    }

    fun resetProfile() {
        try {
            Logger.debug("Klaviyo", "Resetting profile")
            Klaviyo.resetProfile()
            Logger.debug("Klaviyo", "Profile reset successfully")
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to reset profile, error: ${e.message}", e)
        }
    }

    fun setPushToken(token: String) {
        try {
            Logger.debug("Klaviyo", "Setting push token")
            if (token.isNotEmpty()) {
                Klaviyo.setPushToken(token)
                Logger.debug("Klaviyo", "Push token set successfully")
            }
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set push token, error: ${e.message}", e)
        }
    }

    fun getPushToken(): String? {
        return try {
            Logger.debug("Klaviyo", "Getting push token")
            Klaviyo.getPushToken()
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to get push token, error: ${e.message}", e)
            null
        }
    }

    fun setBadgeCount(count: Int) {
        // setBadgeCount is iOS only, not supported on Android
        Logger.warn("Klaviyo", "setBadgeCount is not supported on Android (iOS only)")
    }

    fun createEvent(eventData: JSObject) {
        try {
            // Event name is required
            val eventName = eventData.getString("name")
            if (eventName.isNullOrEmpty()) {
                Logger.error("Klaviyo", "Event name is required", IllegalArgumentException("Event name is required"))
                return
            }
            
            // Create event with name (using String constructor)
            val event = Event(eventName)
            
            // Add value if present
            if (eventData.has("value")) {
                val valueObj = eventData.getDouble("value")
                event.setProperty(EventKey.VALUE, valueObj)
            }
            
            // Add uniqueId if present
            if (eventData.has("uniqueId")) {
                val uniqueId = eventData.getString("uniqueId")
                if (!uniqueId.isNullOrEmpty()) {
                    event.setProperty(EventKey.EVENT_ID, uniqueId)
                }
            }
            
            // Add properties if present
            if (eventData.has("properties")) {
                try {
                    val properties = eventData.getJSObject("properties")
                    if (properties != null) {
                        val keysIterator = properties.keys()
                        while (keysIterator.hasNext()) {
                            val key = keysIterator.next()
                            if (!key.isNullOrEmpty()) {
                                try {
                                    val value = properties.get(key)
                                    // Convert value to Serializable if needed
                                    val serializableValue: Any? = when (value) {
                                        is org.json.JSONArray -> value
                                        is org.json.JSONObject -> value
                                        is String -> value
                                        is Number -> value
                                        is Boolean -> value
                                        null -> null
                                        else -> {
                                            // Try to convert to JSON if it's a Map or List
                                            try {
                                                when (value) {
                                                    is Map<*, *> -> {
                                                        val jsonObj = org.json.JSONObject()
                                                        for ((k, v) in value) {
                                                            jsonObj.put(k.toString(), v)
                                                        }
                                                        jsonObj
                                                    }
                                                    is List<*> -> {
                                                        val jsonArray = org.json.JSONArray()
                                                        for (item in value) {
                                                            jsonArray.put(item)
                                                        }
                                                        jsonArray
                                                    }
                                                    else -> value.toString()
                                                }
                                            } catch (e: Exception) {
                                                Logger.debug("Klaviyo", "Failed to convert property value for key: $key, using string representation, error: ${e.message}")
                                                value.toString()
                                            }
                                        }
                                    }
                                    
                                    // Set property - JSONArray and JSONObject are Serializable, as are primitives
                                    if (serializableValue != null) {
                                        if (serializableValue is java.io.Serializable) {
                                            event.setProperty(key, serializableValue)
                                        } else {
                                            // Fallback: convert to string if not Serializable
                                            val stringValue = serializableValue.toString()
                                            event.setProperty(key, stringValue)
                                        }
                                    }
                                } catch (e: org.json.JSONException) {
                                    Logger.debug("Klaviyo", "Failed to get property value for key: $key, error: ${e.message}")
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    Logger.error("Klaviyo", "Failed to process event properties, error: ${e.message}", e)
                }
            }
            
            // Track the event
            Klaviyo.createEvent(event)
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to create event, error: ${e.message}", e)
        }
    }

    fun registerForInAppForms(configuration: JSObject?) {
        try {
            Logger.debug("Klaviyo", "Registering for in-app forms")
            
            // Extract sessionTimeoutDuration if provided
            val sessionTimeoutSeconds: Long? = configuration?.let { config ->
                if (config.has("sessionTimeoutDuration")) {
                    try {
                        val seconds = config.getLong("sessionTimeoutDuration")
                        if (seconds > 0) seconds else null
                    } catch (e: org.json.JSONException) {
                        Logger.warn("Klaviyo", "Failed to parse sessionTimeoutDuration: ${e.message}, using default config.")
                        null
                    }
                } else {
                    null
                }
            }
            
            // Now that we're in Kotlin, we can directly create InAppFormsConfig!
            val config = if (sessionTimeoutSeconds != null) {
                InAppFormsConfig(sessionTimeoutDuration = sessionTimeoutSeconds.seconds)
            } else {
                InAppFormsConfig()
            }

            // Register for in-app forms using Klaviyo SDK extension function
            Klaviyo.registerForInAppForms(config)
            Logger.debug("Klaviyo", "Registered for in-app forms successfully")
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to register for in-app forms, error: ${e.message}", e)
        }
    }

    fun unregisterFromInAppForms() {
        try {
            Logger.debug("Klaviyo", "Unregistering from in-app forms")
            Klaviyo.unregisterFromInAppForms()
            Logger.debug("Klaviyo", "Unregistered from in-app forms successfully")
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to unregister from in-app forms, error: ${e.message}", e)
        }
    }

    fun registerDeepLinkHandler(handler: (uri: Uri) -> Unit) {
        try {
            Logger.debug("Klaviyo", "Registering deep link handler")
            Klaviyo.registerDeepLinkHandler { uri ->
                try {
                    handler(uri)
                } catch (inner: Exception) {
                    Logger.error("Klaviyo", "Error in deep link handler callback: ${inner.message}", inner)
                }
            }
            Logger.debug("Klaviyo", "Deep link handler registered")
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to register deep link handler, error: ${e.message}", e)
        }
    }

    fun unregisterDeepLinkHandler() {
        try {
            Logger.debug("Klaviyo", "Unregistering deep link handler")
            Klaviyo.unregisterDeepLinkHandler()
            Logger.debug("Klaviyo", "Deep link handler unregistered")
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to unregister deep link handler, error: ${e.message}", e)
        }
    }

    fun handleUniversalTrackingLink(intent: Intent?): Boolean {
        return try {
            Logger.debug("Klaviyo", "Handling universal tracking link via SDK")
            if (Klaviyo.handleUniversalTrackingLink(intent)) {
                Logger.debug("Klaviyo", "Klaviyo SDK accepted universal tracking link")
                true
            } else {
                Logger.debug("Klaviyo", "Klaviyo SDK did not handle universal tracking link")
                false
            }
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to handle universal tracking link, error: ${e.message}", e)
            false
        }
    }
    
    private fun setLocationAttributes(klaviyo: Klaviyo, location: JSObject) {
        try {
            setAttributeIfPresent(klaviyo, location, "address1", ProfileKey.ADDRESS1)
            setAttributeIfPresent(klaviyo, location, "address2", ProfileKey.ADDRESS2)
            setAttributeIfPresent(klaviyo, location, "city", ProfileKey.CITY)
            setAttributeIfPresent(klaviyo, location, "region", ProfileKey.REGION)
            setAttributeIfPresent(klaviyo, location, "zip", ProfileKey.ZIP)
            setAttributeIfPresent(klaviyo, location, "country", ProfileKey.COUNTRY)
            setAttributeIfPresent(klaviyo, location, "timezone", ProfileKey.TIMEZONE)
            setAttributeIfPresent(klaviyo, location, "latitude", ProfileKey.LATITUDE)
            setAttributeIfPresent(klaviyo, location, "longitude", ProfileKey.LONGITUDE)
        } catch (e: Exception) {
            Logger.error("Klaviyo", "Failed to set location attributes, error: ${e.message}", e)
        }
    }
    
    private fun setCustomProperties(klaviyo: Klaviyo, properties: JSObject) {
        // Iterate through all keys in the properties object
        val keysIterator = properties.keys()
        while (keysIterator.hasNext()) {
            val key = keysIterator.next()
            if (key.isNullOrEmpty()) {
                continue
            }
            try {
                if (properties.has(key)) {
                    val value = properties.get(key)
                    val valueStr = value.toString()
                    if (valueStr.isNotEmpty()) {
                        try {
                            val customKey = ProfileKey.CUSTOM(key)
                            klaviyo.setProfileAttribute(customKey, valueStr)
                        } catch (e: Exception) {
                            Logger.error("Klaviyo", "Failed to set custom property: $key, error: ${e.message}", e)
                        }
                    }
                }
            } catch (e: org.json.JSONException) {
                Logger.error("Klaviyo", "Failed to get property value for key: $key, error: ${e.message}", e)
            } catch (e: Exception) {
                Logger.error("Klaviyo", "Unexpected error processing custom property: $key, error: ${e.message}", e)
            }
        }
    }
}

