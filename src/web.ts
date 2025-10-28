import { WebPlugin } from '@capacitor/core';

import type {
  KlaviyoEvent,
  KlaviyoFormConfiguration,
  KlaviyoProfile,
  TectonicKlaviyoPlugin
} from './definitions';

class TectonicKlaviyoWeb extends WebPlugin implements TectonicKlaviyoPlugin {
  async initialize(options: { apiKey: string }): Promise<void> {
    console.log('Klaviyo initialize (web)', options);
    throw this.unimplemented('initialize is not implemented on web');
  }

  async setProfile(options: KlaviyoProfile): Promise<void> {
    console.log('Klaviyo setProfile (web)', options);
    throw this.unimplemented('setProfile is not implemented on web');
  }

  async setExternalId(options: { externalId: string }): Promise<void> {
    console.log('Klaviyo setExternalId (web)', options);
    throw this.unimplemented('setExternalId is not implemented on web');
  }

  async getExternalId(): Promise<{ externalId: string | null }> {
    console.log('Klaviyo getExternalId (web)');
    throw this.unimplemented('getExternalId is not implemented on web');
  }

  async setEmail(options: { email: string }): Promise<void> {
    console.log('Klaviyo setEmail (web)', options);
    throw this.unimplemented('setEmail is not implemented on web');
  }

  async getEmail(): Promise<{ email: string | null }> {
    console.log('Klaviyo getEmail (web)');
    throw this.unimplemented('getEmail is not implemented on web');
  }

  async setPhoneNumber(options: { phoneNumber: string }): Promise<void> {
    console.log('Klaviyo setPhoneNumber (web)', options);
    throw this.unimplemented('setPhoneNumber is not implemented on web');
  }

  async getPhoneNumber(): Promise<{ phoneNumber: string | null }> {
    console.log('Klaviyo getPhoneNumber (web)');
    throw this.unimplemented('getPhoneNumber is not implemented on web');
  }

  async setProfileAttribute(options: {
    propertyKey: string;
    value: string;
  }): Promise<void> {
    console.log('Klaviyo setProfileAttribute (web)', options);
    throw this.unimplemented('setProfileAttribute is not implemented on web');
  }

  async resetProfile(): Promise<void> {
    console.log('Klaviyo resetProfile (web)');
    throw this.unimplemented('resetProfile is not implemented on web');
  }

  async setPushToken(options: { token: string }): Promise<void> {
    console.log('Klaviyo setPushToken (web)', options);
    throw this.unimplemented('setPushToken is not implemented on web');
  }

  async getPushToken(): Promise<{ token: string | null }> {
    console.log('Klaviyo getPushToken (web)');
    throw this.unimplemented('getPushToken is not implemented on web');
  }

  async setBadgeCount(options: { count: number }): Promise<void> {
    console.log('Klaviyo setBadgeCount (web)', options);
    throw this.unimplemented('setBadgeCount is not implemented on web');
  }

  async createEvent(options: KlaviyoEvent): Promise<void> {
    console.log('Klaviyo createEvent (web)', options);
    throw this.unimplemented('createEvent is not implemented on web');
  }

  async registerForInAppForms(options?: {
    configuration?: KlaviyoFormConfiguration;
  }): Promise<void> {
    console.log('Klaviyo registerForInAppForms (web)', options);
    throw this.unimplemented('registerForInAppForms is not implemented on web');
  }

  async unregisterFromInAppForms(): Promise<void> {
    console.log('Klaviyo unregisterFromInAppForms (web)');
    throw this.unimplemented(
      'unregisterFromInAppForms is not implemented on web'
    );
  }

  async handleUniversalTrackingLink(options: {
    trackingLink: string | null;
  }): Promise<{ handled: boolean }> {
    console.log('Klaviyo handleUniversalTrackingLink (web)', options);
    return { handled: false };
  }
}

export { TectonicKlaviyoWeb };
