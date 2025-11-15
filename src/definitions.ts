import type { Plugin } from '@capacitor/core';

/**
 * Common clientside event metrics recognized by Klaviyo.
 * Custom metrics can be defined by using any string value not in this enum.
 */
enum KlaviyoEventMetric {
  /**
   * Metric for when the app is opened
   */
  OPENED_APP = 'Opened App',
  /**
   * Metric for when a product is viewed
   */
  VIEWED_PRODUCT = 'Viewed Product',
  /**
   * Metric for when an item is added to cart
   */
  ADDED_TO_CART = 'Added to Cart',
  /**
   * Metric for when checkout is started
   */
  STARTED_CHECKOUT = 'Started Checkout'
}

// Events emitted by the native plugin
type TectonicKlaviyoEvent = 'klaviyoDeepLink';

// Payloads for each event
type TectonicKlaviyoDeepLinkEventPayload = { uri: string };

type KlaviyoFormConfiguration = {
  sessionTimeoutDuration: number;
};

/**
 * Interface for location information of a profile
 */
type KlaviyoLocation = {
  /**
   * First line of street address
   */
  address1?: string;
  /**
   * Second line of street address
   */
  address2?: string;
  /**
   * City name
   */
  city?: string;
  /**
   * Country name
   */
  country?: string;
  /**
   * Zip code
   */
  zip?: string;
  /**
   * Region within a country, such as state or province
   */
  region?: string;
  /**
   * Latitude coordinate. We recommend providing a precision of four decimal places.
   */
  latitude?: number;
  /**
   * Longitude coordinate. We recommend providing a precision of four decimal places.
   */
  longitude?: number;
  /**
   * Time zone name. We recommend using time zones from the IANA Time Zone Database.
   */
  timezone?: string;
};

/**
 * Type for profile properties
 */
type KlaviyoProfileProperties = Record<string, any>;

/**
 * Interface for a profile
 */
type KlaviyoProfile = {
  /**
   * A unique identifier used by customers to associate Klaviyo profiles with profiles in an external system, such as a point-of-sale system.
   */
  externalId?: string;
  /**
   * Individual's email address
   */
  email?: string;
  /**
   * Individual's phone number in E.164 format
   */
  phoneNumber?: string;
  /**
   * Individual's first name
   */
  firstName?: string;
  /**
   * Individual's last name
   */
  lastName?: string;
  /**
   * Individual's job title
   */
  title?: string;
  /**
   * Name of the company or organization within the company for whom the individual works
   */
  organization?: string;
  /**
   * URL pointing to the location of a profile image
   */
  image?: string;
  /**
   * An object containing location information for this profile
   */
  location?: KlaviyoLocation;
  /**
   * An object containing key/value pairs for any custom properties assigned to this profile
   */
  properties?: KlaviyoProfileProperties;
};

/**
 * Type for event properties
 */
type KlaviyoEventProperties = Record<string, any>;

/**
 * Interface for an event
 */
type KlaviyoEvent = {
  /**
   * Name of the event. Must be less than 128 characters.
   */
  name: string;
  /**
   * A numeric value to associate with this event. For example, the dollar amount of a purchase.
   */
  value?: number;
  /**
   * A unique identifier for an event. If the uniqueId is repeated for the same
   * profile and metric, only the first processed event will be recorded. If this is not
   * present, this will use the time to the second. Using the default, this limits only one
   * event per profile per second.
   */
  uniqueId?: string;
  /**
   * Properties of this event. Any top level property (that are not objects) can be
   * used to create segments. The $extra property is a special property. This records any
   * non-segmentable values that can be referenced later. For example, HTML templates are
   * useful on a segment but are not used to create a segment. There are limits
   * placed onto the size of the data present. This must not exceed 5 MB. This must not
   * exceed 300 event properties. A single string cannot be larger than 100 KB. Each array
   * must not exceed 4000 elements. The properties cannot contain more than 10 nested levels.
   */
  properties?: KlaviyoEventProperties;
};

interface TectonicKlaviyoPlugin extends Plugin {
  /**
   * Initializes the Klaviyo SDK with the given API key.
   * @param options - Contains the public API key
   */
  initialize(options: { apiKey: string }): Promise<void>;

  /**
   * Create and update properties about a profile without tracking an associated event.
   * @param options - The profile object to set
   */
  setProfile(options: KlaviyoProfile): Promise<void>;

  /**
   * Update a profile's external ID.
   * @param options - Contains the external ID to set
   */
  setExternalId(options: { externalId: string }): Promise<void>;

  /**
   * Retrieve a profile's external ID.
   * @returns The external ID or null
   */
  getExternalId(): Promise<{ externalId: string | null }>;

  /**
   * Update a profile's email address.
   * @param options - Contains the email address to set
   */
  setEmail(options: { email: string }): Promise<void>;

  /**
   * Retrieve a profile's email address.
   * @returns The email address or null
   */
  getEmail(): Promise<{ email: string | null }>;

  /**
   * Update a profile's phone number.
   * @param options - Contains the phone number to set
   */
  setPhoneNumber(options: { phoneNumber: string }): Promise<void>;

  /**
   * Retrieve a profile's phone number.
   * @returns The phone number or null
   */
  getPhoneNumber(): Promise<{ phoneNumber: string | null }>;

  /**
   * Update a profile's properties.
   * @param options - Contains the property key and value to set
   */
  setProfileAttribute(options: {
    propertyKey: string;
    value: string;
  }): Promise<void>;

  /**
   * Clear the current profile and set it to a new anonymous profile
   */
  resetProfile(): Promise<void>;

  /**
   * Set the push token for the current profile
   * @param options - Contains the push token
   */
  setPushToken(options: { token: string }): Promise<void>;

  /**
   * Get the push token for the current profile from the SDK
   * @returns The push token or null
   */
  getPushToken(): Promise<{ token: string | null }>;

  /**
   * Set the badge count for the app icon
   * @param options - Contains the badge count
   */
  setBadgeCount(options: { count: number }): Promise<void>;

  /**
   * Create a new event to track a profile's activity.
   * @param options - The event to track
   */
  createEvent(options: KlaviyoEvent): Promise<void>;

  /**
   * Load in-app forms data and display a form to the user if applicable based on the forms
   * configured in your Klaviyo account. Note initialize must be called first
   * @param options - Optional configuration with session timeout duration in seconds
   */
  registerForInAppForms(options?: {
    configuration?: KlaviyoFormConfiguration;
  }): Promise<void>;

  /**
   * Unregisters app from receiving in-app forms.
   */
  unregisterFromInAppForms(): Promise<void>;

  /**
   * Resolves a Klaviyo tracking link to a Universal Link URL,
   * then handles navigation to the resolved URL.
   * The link must be a valid Klaviyo universal tracking link:
   * - Uses HTTPS protocol
   * - Path starts with '/u/'
   * @param options - Contains the tracking link to be handled
   * @returns Whether the link was handled successfully
   */
  handleUniversalTrackingLink(options: {
    trackingLink: string | null;
  }): Promise<{ handled: boolean }>;

  /**
   * Register a deep link handler. The plugin will emit a `klaviyoDeepLink` event
   * with payload `{ uri: string }` whenever the SDK provides a deep link
   * from push, forms, or universal tracking links.
   */
  registerDeepLinkHandler(): Promise<void>;

  /**
   * Unregister the native deep link handler.
   */
  unregisterDeepLinkHandler(): Promise<void>;
}

export { KlaviyoEventMetric };

export type {
  KlaviyoEvent,
  KlaviyoEventProperties,
  KlaviyoFormConfiguration,
  KlaviyoLocation,
  KlaviyoProfile,
  KlaviyoProfileProperties,
  TectonicKlaviyoDeepLinkEventPayload,
  TectonicKlaviyoEvent,
  TectonicKlaviyoPlugin
};
