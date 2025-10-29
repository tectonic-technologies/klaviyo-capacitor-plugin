package com.tectonic.klaviyo;

import android.content.Context;
import androidx.activity.ComponentActivity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.klaviyo.analytics.Klaviyo;
import com.klaviyo.analytics.model.ProfileKey;
import org.json.JSONException;

import java.util.Iterator;

public class TectonicKlaviyo {

    private Klaviyo getKlaviyoInstance() {
        return Klaviyo.INSTANCE;
    }

    private void setAttributeIfPresent(Klaviyo klaviyo, JSObject data, String key, ProfileKey profileKey) {
        try {
            if (data.has(key)) {
                String value = data.getString(key);
                if (value != null && !value.isEmpty()) {
                    klaviyo.setProfileAttribute(profileKey, value);
                }
            }
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set profile attribute: " + key + ", error: " + e.getMessage(), e);
        }
    }

    private void setIdentifierIfPresent(Klaviyo klaviyo, JSObject data, String key, String methodName) {
        try {
            if (data.has(key)) {
                String value = data.getString(key);
                if (value != null && !value.isEmpty()) {
                    if ("externalId".equals(methodName)) {
                        klaviyo.setExternalId(value);
                    } else if ("email".equals(methodName)) {
                        klaviyo.setEmail(value);
                    } else if ("phoneNumber".equals(methodName)) {
                        klaviyo.setPhoneNumber(value);
                    }
                }
            }
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set identifier: " + key + ", error: " + e.getMessage(), e);
        }
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

    public void setProfile(JSObject profileData) {
        try {
            Logger.debug("Klaviyo", "Setting profile data");
            Klaviyo klaviyo = getKlaviyoInstance();
            
            // Set identifiers using helper method
            setIdentifierIfPresent(klaviyo, profileData, "externalId", "externalId");
            setIdentifierIfPresent(klaviyo, profileData, "email", "email");
            setIdentifierIfPresent(klaviyo, profileData, "phoneNumber", "phoneNumber");
            
            // Set profile attributes using helper method
            setAttributeIfPresent(klaviyo, profileData, "firstName", ProfileKey.FIRST_NAME.INSTANCE);
            setAttributeIfPresent(klaviyo, profileData, "lastName", ProfileKey.LAST_NAME.INSTANCE);
            setAttributeIfPresent(klaviyo, profileData, "title", ProfileKey.TITLE.INSTANCE);
            setAttributeIfPresent(klaviyo, profileData, "organization", ProfileKey.ORGANIZATION.INSTANCE);
            setAttributeIfPresent(klaviyo, profileData, "image", ProfileKey.IMAGE.INSTANCE);
            
            // Set location if present
            try {
                if (profileData.has("location")) {
                    JSObject location = profileData.getJSObject("location");
                    if (location != null) {
                        setLocationAttributes(klaviyo, location);
                    }
                }
            } catch (Exception e) {
                Logger.error("Klaviyo", "Failed to set location data, error: " + e.getMessage(), e);
            }
            
            // Set custom properties
            try {
                if (profileData.has("properties")) {
                    JSObject properties = profileData.getJSObject("properties");
                    if (properties != null) {
                        setCustomProperties(klaviyo, properties);
                    }
                }
            } catch (Exception e) {
                Logger.error("Klaviyo", "Failed to set custom properties, error: " + e.getMessage(), e);
            }
            
            Logger.debug("Klaviyo", "Profile data set successfully");
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set profile data, error: " + e.getMessage(), e);
        }
    }

    public void setExternalId(String externalId) {
        try {
            Logger.debug("Klaviyo", "Setting external ID");
            Klaviyo klaviyo = getKlaviyoInstance();
            if (externalId != null && !externalId.isEmpty()) {
                klaviyo.setExternalId(externalId);
                Logger.debug("Klaviyo", "External ID set successfully");
            }
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set external ID, error: " + e.getMessage(), e);
        }
    }

    public String getExternalId() {
        try {
            Logger.debug("Klaviyo", "Getting external ID");
            Klaviyo klaviyo = getKlaviyoInstance();
            return klaviyo.getExternalId();
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to get external ID, error: " + e.getMessage(), e);
            return null;
        }
    }

    public void setEmail(String email) {
        try {
            Logger.debug("Klaviyo", "Setting email");
            Klaviyo klaviyo = getKlaviyoInstance();
            if (email != null && !email.isEmpty()) {
                klaviyo.setEmail(email);
                Logger.debug("Klaviyo", "Email set successfully");
            }
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set email, error: " + e.getMessage(), e);
        }
    }

    public String getEmail() {
        try {
            Logger.debug("Klaviyo", "Getting email");
            Klaviyo klaviyo = getKlaviyoInstance();
            return klaviyo.getEmail();
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to get email, error: " + e.getMessage(), e);
            return null;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        try {
            Logger.debug("Klaviyo", "Setting phone number");
            Klaviyo klaviyo = getKlaviyoInstance();
            if (phoneNumber != null && !phoneNumber.isEmpty()) {
                klaviyo.setPhoneNumber(phoneNumber);
                Logger.debug("Klaviyo", "Phone number set successfully");
            }
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set phone number, error: " + e.getMessage(), e);
        }
    }

    public String getPhoneNumber() {
        try {
            Logger.debug("Klaviyo", "Getting phone number");
            Klaviyo klaviyo = getKlaviyoInstance();
            return klaviyo.getPhoneNumber();
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to get phone number, error: " + e.getMessage(), e);
            return null;
        }
    }

    private ProfileKey getProfileKey(String propertyKey) {
        // Map standard attribute keys to ProfileKey constants
        if ("firstName".equals(propertyKey) || "first_name".equals(propertyKey)) {
            return ProfileKey.FIRST_NAME.INSTANCE;
        } else if ("lastName".equals(propertyKey) || "last_name".equals(propertyKey)) {
            return ProfileKey.LAST_NAME.INSTANCE;
        } else if ("title".equals(propertyKey)) {
            return ProfileKey.TITLE.INSTANCE;
        } else if ("organization".equals(propertyKey)) {
            return ProfileKey.ORGANIZATION.INSTANCE;
        } else if ("image".equals(propertyKey)) {
            return ProfileKey.IMAGE.INSTANCE;
        } else if ("address1".equals(propertyKey)) {
            return ProfileKey.ADDRESS1.INSTANCE;
        } else if ("address2".equals(propertyKey)) {
            return ProfileKey.ADDRESS2.INSTANCE;
        } else if ("city".equals(propertyKey)) {
            return ProfileKey.CITY.INSTANCE;
        } else if ("region".equals(propertyKey)) {
            return ProfileKey.REGION.INSTANCE;
        } else if ("zip".equals(propertyKey)) {
            return ProfileKey.ZIP.INSTANCE;
        } else if ("country".equals(propertyKey)) {
            return ProfileKey.COUNTRY.INSTANCE;
        } else if ("timezone".equals(propertyKey)) {
            return ProfileKey.TIMEZONE.INSTANCE;
        } else if ("latitude".equals(propertyKey)) {
            return ProfileKey.LATITUDE.INSTANCE;
        } else if ("longitude".equals(propertyKey)) {
            return ProfileKey.LONGITUDE.INSTANCE;
        }
        // For any other key, use CUSTOM
        return new ProfileKey.CUSTOM(propertyKey);
    }

    public void setProfileAttribute(String propertyKey, String value) {
        try {
            Logger.debug("Klaviyo", "Setting profile attribute: " + propertyKey);
            Klaviyo klaviyo = getKlaviyoInstance();
            
            if (propertyKey == null || propertyKey.isEmpty()) {
                Logger.error("Klaviyo", "Property key cannot be null or empty", new IllegalArgumentException("Property key cannot be null or empty"));
                return;
            }
            
            if (value == null || value.isEmpty()) {
                Logger.debug("Klaviyo", "Value is null or empty, skipping profile attribute: " + propertyKey);
                return;
            }
            
            // Get the appropriate ProfileKey (standard or custom)
            ProfileKey key = getProfileKey(propertyKey);
            klaviyo.setProfileAttribute(key, value);
            Logger.debug("Klaviyo", "Profile attribute set successfully: " + propertyKey);
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set profile attribute: " + propertyKey + ", error: " + e.getMessage(), e);
        }
    }
    
    private void setLocationAttributes(Klaviyo klaviyo, JSObject location) {
        try {
            setAttributeIfPresent(klaviyo, location, "address1", ProfileKey.ADDRESS1.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "address2", ProfileKey.ADDRESS2.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "city", ProfileKey.CITY.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "region", ProfileKey.REGION.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "zip", ProfileKey.ZIP.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "country", ProfileKey.COUNTRY.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "timezone", ProfileKey.TIMEZONE.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "latitude", ProfileKey.LATITUDE.INSTANCE);
            setAttributeIfPresent(klaviyo, location, "longitude", ProfileKey.LONGITUDE.INSTANCE);
        } catch (Exception e) {
            Logger.error("Klaviyo", "Failed to set location attributes, error: " + e.getMessage(), e);
        }
    }
    
    private void setCustomProperties(Klaviyo klaviyo, JSObject properties) {
        // Iterate through all keys in the properties object
        for (Iterator<String> it = properties.keys(); it.hasNext(); ) {
            String key = it.next();
            if (key == null || key.isEmpty()) {
                continue;
            }
            try {
                if (properties.has(key)) {
                    Object value = properties.get(key);
                    String valueStr = value.toString();
                    if (!valueStr.isEmpty()) {
                        try {
                            ProfileKey customKey = new ProfileKey.CUSTOM(key);
                            klaviyo.setProfileAttribute(customKey, valueStr);
                        } catch (Exception e) {
                            Logger.error("Klaviyo", "Failed to set custom property: " + key + ", error: " + e.getMessage(), e);
                        }
                    }
                }
            } catch (JSONException e) {
                Logger.error("Klaviyo", "Failed to get property value for key: " + key + ", error: " + e.getMessage(), e);
            } catch (Exception e) {
                Logger.error("Klaviyo", "Unexpected error processing custom property: " + key + ", error: " + e.getMessage(), e);
            }
        }
    }
}
