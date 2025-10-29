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

}
