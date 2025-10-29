package com.tectonic.klaviyo;

import android.content.Context;
import android.app.Activity;
import androidx.activity.ComponentActivity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.klaviyo.analytics.Klaviyo;

public class TectonicKlaviyo {

    private Klaviyo getKlaviyoInstance() {
        // The Klaviyo instance is a singleton
        // Klaviyo klaviyo = Klaviyo.getInstance();
        return Klaviyo.INSTANCE;
    }

    public void initialize(Context context, String apiKey) {

        if (!(context instanceof ComponentActivity)) {
            throw new IllegalArgumentException("Context must be a ComponentActivity");
        }
        Logger.debug("Klaviyo", "Initializing Klaviyo SDK");
        Klaviyo klaviyo = getKlaviyoInstance();
        klaviyo.initialize(apiKey, context);
        Logger.debug("Klaviyo", "Klaviyo SDK initialized successfully");
    }
}
