import { WebPlugin } from '@capacitor/core';

import type { TectonicKlaviyoPlugin } from './definitions';

export class TectonicKlaviyoWeb extends WebPlugin implements TectonicKlaviyoPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
