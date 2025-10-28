export interface TectonicKlaviyoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
