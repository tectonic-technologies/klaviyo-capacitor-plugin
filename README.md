# klaviyo-capacitor-plugin

A capacitor plugin for Klaviyo

## Install

```bash
npm install klaviyo-capacitor-plugin
npx cap sync
```

### Android

Add following in applications settings.gradle

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```

## API

<docgen-index>

* [`initialize(...)`](#initialize)
* [`setProfile(...)`](#setprofile)
* [`setExternalId(...)`](#setexternalid)
* [`getExternalId()`](#getexternalid)
* [`setEmail(...)`](#setemail)
* [`getEmail()`](#getemail)
* [`setPhoneNumber(...)`](#setphonenumber)
* [`getPhoneNumber()`](#getphonenumber)
* [`setProfileAttribute(...)`](#setprofileattribute)
* [`resetProfile()`](#resetprofile)
* [`setPushToken(...)`](#setpushtoken)
* [`getPushToken()`](#getpushtoken)
* [`setBadgeCount(...)`](#setbadgecount)
* [`createEvent(...)`](#createevent)
* [`registerForInAppForms(...)`](#registerforinappforms)
* [`unregisterFromInAppForms()`](#unregisterfrominappforms)
* [`handleUniversalTrackingLink(...)`](#handleuniversaltrackinglink)
* [`registerDeepLinkHandler()`](#registerdeeplinkhandler)
* [`unregisterDeepLinkHandler()`](#unregisterdeeplinkhandler)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### initialize(...)

```typescript
initialize(options: { apiKey: string; }) => Promise<void>
```

Initializes the Klaviyo SDK with the given API key.

| Param         | Type                             | Description                   |
| ------------- | -------------------------------- | ----------------------------- |
| **`options`** | <code>{ apiKey: string; }</code> | - Contains the public API key |

--------------------


### setProfile(...)

```typescript
setProfile(options: KlaviyoProfile) => Promise<void>
```

Create and update properties about a profile without tracking an associated event.

| Param         | Type                                                      | Description                 |
| ------------- | --------------------------------------------------------- | --------------------------- |
| **`options`** | <code><a href="#klaviyoprofile">KlaviyoProfile</a></code> | - The profile object to set |

--------------------


### setExternalId(...)

```typescript
setExternalId(options: { externalId: string; }) => Promise<void>
```

Update a profile's external ID.

| Param         | Type                                 | Description                       |
| ------------- | ------------------------------------ | --------------------------------- |
| **`options`** | <code>{ externalId: string; }</code> | - Contains the external ID to set |

--------------------


### getExternalId()

```typescript
getExternalId() => Promise<{ externalId: string | null; }>
```

Retrieve a profile's external ID.

**Returns:** <code>Promise&lt;{ externalId: string | null; }&gt;</code>

--------------------


### setEmail(...)

```typescript
setEmail(options: { email: string; }) => Promise<void>
```

Update a profile's email address.

| Param         | Type                            | Description                         |
| ------------- | ------------------------------- | ----------------------------------- |
| **`options`** | <code>{ email: string; }</code> | - Contains the email address to set |

--------------------


### getEmail()

```typescript
getEmail() => Promise<{ email: string | null; }>
```

Retrieve a profile's email address.

**Returns:** <code>Promise&lt;{ email: string | null; }&gt;</code>

--------------------


### setPhoneNumber(...)

```typescript
setPhoneNumber(options: { phoneNumber: string; }) => Promise<void>
```

Update a profile's phone number.

| Param         | Type                                  | Description                        |
| ------------- | ------------------------------------- | ---------------------------------- |
| **`options`** | <code>{ phoneNumber: string; }</code> | - Contains the phone number to set |

--------------------


### getPhoneNumber()

```typescript
getPhoneNumber() => Promise<{ phoneNumber: string | null; }>
```

Retrieve a profile's phone number.

**Returns:** <code>Promise&lt;{ phoneNumber: string | null; }&gt;</code>

--------------------


### setProfileAttribute(...)

```typescript
setProfileAttribute(options: { propertyKey: string; value: string; }) => Promise<void>
```

Update a profile's properties.

| Param         | Type                                                 | Description                                  |
| ------------- | ---------------------------------------------------- | -------------------------------------------- |
| **`options`** | <code>{ propertyKey: string; value: string; }</code> | - Contains the property key and value to set |

--------------------


### resetProfile()

```typescript
resetProfile() => Promise<void>
```

Clear the current profile and set it to a new anonymous profile

--------------------


### setPushToken(...)

```typescript
setPushToken(options: { token: string; }) => Promise<void>
```

Set the push token for the current profile

| Param         | Type                            | Description               |
| ------------- | ------------------------------- | ------------------------- |
| **`options`** | <code>{ token: string; }</code> | - Contains the push token |

--------------------


### getPushToken()

```typescript
getPushToken() => Promise<{ token: string | null; }>
```

Get the push token for the current profile from the SDK

**Returns:** <code>Promise&lt;{ token: string | null; }&gt;</code>

--------------------


### setBadgeCount(...)

```typescript
setBadgeCount(options: { count: number; }) => Promise<void>
```

Set the badge count for the app icon

| Param         | Type                            | Description                |
| ------------- | ------------------------------- | -------------------------- |
| **`options`** | <code>{ count: number; }</code> | - Contains the badge count |

--------------------


### createEvent(...)

```typescript
createEvent(options: KlaviyoEvent) => Promise<void>
```

Create a new event to track a profile's activity.

| Param         | Type                                                  | Description          |
| ------------- | ----------------------------------------------------- | -------------------- |
| **`options`** | <code><a href="#klaviyoevent">KlaviyoEvent</a></code> | - The event to track |

--------------------


### registerForInAppForms(...)

```typescript
registerForInAppForms(options?: { configuration?: KlaviyoFormConfiguration | undefined; } | undefined) => Promise<void>
```

Load in-app forms data and display a form to the user if applicable based on the forms
configured in your Klaviyo account. Note initialize must be called first

| Param         | Type                                                                                               | Description                                                       |
| ------------- | -------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------- |
| **`options`** | <code>{ configuration?: <a href="#klaviyoformconfiguration">KlaviyoFormConfiguration</a>; }</code> | - Optional configuration with session timeout duration in seconds |

--------------------


### unregisterFromInAppForms()

```typescript
unregisterFromInAppForms() => Promise<void>
```

Unregisters app from receiving in-app forms.

--------------------


### handleUniversalTrackingLink(...)

```typescript
handleUniversalTrackingLink(options: { trackingLink: string | null; }) => Promise<{ handled: boolean; }>
```

Resolves a Klaviyo tracking link to a Universal Link URL,
then handles navigation to the resolved URL.
The link must be a valid Klaviyo universal tracking link:
- Uses HTTPS protocol
- Path starts with '/u/'

| Param         | Type                                           | Description                                |
| ------------- | ---------------------------------------------- | ------------------------------------------ |
| **`options`** | <code>{ trackingLink: string \| null; }</code> | - Contains the tracking link to be handled |

**Returns:** <code>Promise&lt;{ handled: boolean; }&gt;</code>

--------------------


### registerDeepLinkHandler()

```typescript
registerDeepLinkHandler() => Promise<void>
```

Register a deep link handler. The plugin will emit a `klaviyoDeepLink` event
with payload `{ uri: string }` whenever the SDK provides a deep link
from push, forms, or universal tracking links.

--------------------


### unregisterDeepLinkHandler()

```typescript
unregisterDeepLinkHandler() => Promise<void>
```

Unregister the native deep link handler.

--------------------


### Type Aliases


#### KlaviyoProfile

Interface for a profile

<code>{ /** * A unique identifier used by customers to associate Klaviyo profiles with profiles in an external system, such as a point-of-sale system. */ externalId?: string; /** * Individual's email address */ email?: string; /** * Individual's phone number in E.164 format */ phoneNumber?: string; /** * Individual's first name */ firstName?: string; /** * Individual's last name */ lastName?: string; /** * Individual's job title */ title?: string; /** * Name of the company or organization within the company for whom the individual works */ organization?: string; /** * URL pointing to the location of a profile image */ image?: string; /** * An object containing location information for this profile */ location?: <a href="#klaviyolocation">KlaviyoLocation</a>; /** * An object containing key/value pairs for any custom properties assigned to this profile */ properties?: <a href="#klaviyoprofileproperties">KlaviyoProfileProperties</a>; }</code>


#### KlaviyoLocation

Interface for location information of a profile

<code>{ /** * First line of street address */ address1?: string; /** * Second line of street address */ address2?: string; /** * City name */ city?: string; /** * Country name */ country?: string; /** * Zip code */ zip?: string; /** * Region within a country, such as state or province */ region?: string; /** * Latitude coordinate. We recommend providing a precision of four decimal places. */ latitude?: number; /** * Longitude coordinate. We recommend providing a precision of four decimal places. */ longitude?: number; /** * Time zone name. We recommend using time zones from the IANA Time Zone Database. */ timezone?: string; }</code>


#### KlaviyoProfileProperties

Type for profile properties

<code><a href="#record">Record</a>&lt;string, any&gt;</code>


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>


#### KlaviyoEvent

Interface for an event

<code>{ /** * Name of the event. Must be less than 128 characters. */ name: string; /** * A numeric value to associate with this event. For example, the dollar amount of a purchase. */ value?: number; /** * A unique identifier for an event. If the uniqueId is repeated for the same * profile and metric, only the first processed event will be recorded. If this is not * present, this will use the time to the second. Using the default, this limits only one * event per profile per second. */ uniqueId?: string; /** * Properties of this event. Any top level property (that are not objects) can be * used to create segments. The $extra property is a special property. This records any * non-segmentable values that can be referenced later. For example, HTML templates are * useful on a segment but are not used to create a segment. There are limits * placed onto the size of the data present. This must not exceed 5 MB. This must not * exceed 300 event properties. A single string cannot be larger than 100 KB. Each array * must not exceed 4000 elements. The properties cannot contain more than 10 nested levels. */ properties?: <a href="#klaviyoeventproperties">KlaviyoEventProperties</a>; }</code>


#### KlaviyoEventProperties

Type for event properties

<code><a href="#record">Record</a>&lt;string, any&gt;</code>


#### KlaviyoFormConfiguration

<code>{ sessionTimeoutDuration: number; }</code>

</docgen-api>
