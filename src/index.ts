import { registerPlugin } from '@capacitor/core';

import type { TectonicKlaviyoPlugin } from './definitions';

const TectonicKlaviyo = registerPlugin<TectonicKlaviyoPlugin>(
  'TectonicKlaviyo',
  {
    web: () => import('./web').then(m => new m.TectonicKlaviyoWeb())
  }
);

export * from './definitions';
export { TectonicKlaviyo };
