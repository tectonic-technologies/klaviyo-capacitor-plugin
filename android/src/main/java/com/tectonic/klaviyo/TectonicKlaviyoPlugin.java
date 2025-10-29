package com.tectonic.klaviyo;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "TectonicKlaviyo")
public class TectonicKlaviyoPlugin extends Plugin {

    private TectonicKlaviyo implementation = new TectonicKlaviyo();

    @PluginMethod
    public void initialize(PluginCall call) {
        String apiKey = call.getString("apiKey");

        if (apiKey == null || apiKey.isEmpty()) {
            call.reject("API key is required");
            return;
        }

        implementation.initialize(getContext(), apiKey);
        call.resolve();
    }

    @PluginMethod
    public void setProfile(PluginCall call) {
        JSObject profileData = call.getData();
        
        if (profileData == null) {
            call.reject("Profile data is required");
            return;
        }

        implementation.setProfile(profileData);
        call.resolve();
    }

    @PluginMethod
    public void setExternalId(PluginCall call) {
        String externalId = call.getString("externalId");
        
        if (externalId == null || externalId.isEmpty()) {
            call.reject("External ID is required");
            return;
        }

        implementation.setExternalId(externalId);
        call.resolve();
    }

    @PluginMethod
    public void getExternalId(PluginCall call) {
        String externalId = implementation.getExternalId();
        
        JSObject result = new JSObject();
        result.put("externalId", externalId);
        call.resolve(result);
    }

    @PluginMethod
    public void setEmail(PluginCall call) {
        String email = call.getString("email");
        
        if (email == null || email.isEmpty()) {
            call.reject("Email is required");
            return;
        }

        implementation.setEmail(email);
        call.resolve();
    }

    @PluginMethod
    public void getEmail(PluginCall call) {
        String email = implementation.getEmail();
        
        JSObject result = new JSObject();
        result.put("email", email);
        call.resolve(result);
    }

    @PluginMethod
    public void setPhoneNumber(PluginCall call) {
        String phoneNumber = call.getString("phoneNumber");
        
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            call.reject("Phone number is required");
            return;
        }

        implementation.setPhoneNumber(phoneNumber);
        call.resolve();
    }

    @PluginMethod
    public void getPhoneNumber(PluginCall call) {
        String phoneNumber = implementation.getPhoneNumber();
        
        JSObject result = new JSObject();
        result.put("phoneNumber", phoneNumber);
        call.resolve(result);
    }

    @PluginMethod
    public void setProfileAttribute(PluginCall call) {
        String propertyKey = call.getString("propertyKey");
        String value = call.getString("value");
        
        if (propertyKey == null || propertyKey.isEmpty()) {
            call.reject("Property key is required");
            return;
        }
        
        if (value == null) {
            call.reject("Value is required");
            return;
        }

        implementation.setProfileAttribute(propertyKey, value);
        call.resolve();
    }

    @PluginMethod
    public void resetProfile(PluginCall call) {
        implementation.resetProfile();
        call.resolve();
    }

    @PluginMethod
    public void setPushToken(PluginCall call) {
        String token = call.getString("token");
        
        if (token == null || token.isEmpty()) {
            call.reject("Token is required");
            return;
        }

        implementation.setPushToken(token);
        call.resolve();
    }

    @PluginMethod
    public void getPushToken(PluginCall call) {
        String token = implementation.getPushToken();
        
        JSObject result = new JSObject();
        result.put("token", token);
        call.resolve(result);
    }

    @PluginMethod
    public void setBadgeCount(PluginCall call) {
        Integer count = call.getInt("count");
        
        if (count == null) {
            call.reject("Count is required");
            return;
        }

        implementation.setBadgeCount(count);
        call.resolve();
    }

    @PluginMethod
    public void createEvent(PluginCall call) {
        JSObject eventData = call.getData();
        
        if (eventData == null) {
            call.reject("Event data is required");
            return;
        }

        implementation.createEvent(eventData);
        call.resolve();
    }

}
