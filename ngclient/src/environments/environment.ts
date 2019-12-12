// this file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// the list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  // https://angular.io/guide/service-worker-getting-started
  enableServiceWorker: false,
  // https://github.com/angular/angular-cli/blob/master/docs/documentation/stories/configure-hmr.md
  hotModuleReplacement: false,
  // web service endpoint
  baseUrl: 'https://localhost:8443'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
import 'zone.js/dist/zone-error'; // included with Angular CLI.
