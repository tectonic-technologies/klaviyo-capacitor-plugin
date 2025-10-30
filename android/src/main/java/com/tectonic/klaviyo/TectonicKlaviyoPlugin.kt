package com.tectonic.klaviyo

import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin

@CapacitorPlugin(name = "TectonicKlaviyo")
class TectonicKlaviyoPlugin : Plugin() {

    private val implementation = TectonicKlaviyo()

    @PluginMethod
    fun initialize(call: PluginCall) {
        val apiKey = call.getString("apiKey")

        if (apiKey.isNullOrEmpty()) {
            call.reject("API key is required")
            return
        }

        implementation.initialize(context, apiKey)
        call.resolve()
    }

    @PluginMethod
    fun setProfile(call: PluginCall) {
        val profileData = call.data
        
        if (profileData == null) {
            call.reject("Profile data is required")
            return
        }

        implementation.setProfile(profileData)
        call.resolve()
    }

    @PluginMethod
    fun setExternalId(call: PluginCall) {
        val externalId = call.getString("externalId")
        
        if (externalId.isNullOrEmpty()) {
            call.reject("External ID is required")
            return
        }

        implementation.setExternalId(externalId)
        call.resolve()
    }

    @PluginMethod
    fun getExternalId(call: PluginCall) {
        val externalId = implementation.getExternalId()
        
        val result = JSObject()
        result.put("externalId", externalId)
        call.resolve(result)
    }

    @PluginMethod
    fun setEmail(call: PluginCall) {
        val email = call.getString("email")
        
        if (email.isNullOrEmpty()) {
            call.reject("Email is required")
            return
        }

        implementation.setEmail(email)
        call.resolve()
    }

    @PluginMethod
    fun getEmail(call: PluginCall) {
        val email = implementation.getEmail()
        
        val result = JSObject()
        result.put("email", email)
        call.resolve(result)
    }

    @PluginMethod
    fun setPhoneNumber(call: PluginCall) {
        val phoneNumber = call.getString("phoneNumber")
        
        if (phoneNumber.isNullOrEmpty()) {
            call.reject("Phone number is required")
            return
        }

        implementation.setPhoneNumber(phoneNumber)
        call.resolve()
    }

    @PluginMethod
    fun getPhoneNumber(call: PluginCall) {
        val phoneNumber = implementation.getPhoneNumber()
        
        val result = JSObject()
        result.put("phoneNumber", phoneNumber)
        call.resolve(result)
    }

    @PluginMethod
    fun setProfileAttribute(call: PluginCall) {
        val propertyKey = call.getString("propertyKey")
        val value = call.getString("value")
        
        if (propertyKey.isNullOrEmpty()) {
            call.reject("Property key is required")
            return
        }
        
        if (value == null) {
            call.reject("Value is required")
            return
        }

        implementation.setProfileAttribute(propertyKey, value)
        call.resolve()
    }

    @PluginMethod
    fun resetProfile(call: PluginCall) {
        implementation.resetProfile()
        call.resolve()
    }

    @PluginMethod
    fun setPushToken(call: PluginCall) {
        val token = call.getString("token")
        
        if (token.isNullOrEmpty()) {
            call.reject("Token is required")
            return
        }

        implementation.setPushToken(token)
        call.resolve()
    }

    @PluginMethod
    fun getPushToken(call: PluginCall) {
        val token = implementation.getPushToken()
        
        val result = JSObject()
        result.put("token", token)
        call.resolve(result)
    }

    @PluginMethod
    fun setBadgeCount(call: PluginCall) {
        val count = call.getInt("count")
        
        if (count == null) {
            call.reject("Count is required")
            return
        }

        implementation.setBadgeCount(count)
        call.resolve()
    }

    @PluginMethod
    fun createEvent(call: PluginCall) {
        val eventData = call.data
        
        if (eventData == null) {
            call.reject("Event data is required")
            return
        }

        implementation.createEvent(eventData)
        call.resolve()
    }

    @PluginMethod
    fun registerForInAppForms(call: PluginCall) {
        val options = call.data
        
        val configuration = if (options != null && options.has("configuration")) {
            options.getJSObject("configuration")
        } else {
            null
        }

        implementation.registerForInAppForms(configuration)
        call.resolve()
    }

    @PluginMethod
    fun unregisterFromInAppForms(call: PluginCall) {
        implementation.unregisterFromInAppForms()
        call.resolve()
    }

    @PluginMethod
    fun handleUniversalTrackingLink(call: PluginCall) {
        // Ignore provided trackingLink on Android; use the current Activity intent per SDK docs
        val handled = implementation.handleUniversalTrackingLink(bridge.activity?.intent)
        
        val result = JSObject()
        result.put("handled", handled)
        call.resolve(result)
    }

    @PluginMethod
    fun registerDeepLinkHandler(call: PluginCall) {
        implementation.registerDeepLinkHandler { uri ->
            val payload = JSObject()
            payload.put("uri", uri.toString())
            notifyListeners("klaviyoDeepLink", payload)
        }
        call.resolve()
    }

    @PluginMethod
    fun unregisterDeepLinkHandler(call: PluginCall) {
        implementation.unregisterDeepLinkHandler()
        call.resolve()
    }

}

