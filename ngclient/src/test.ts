// This file is required by karma.conf.js and
// loads recursively all the.spec and framework files

import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting
} from '@angular/platform-browser-dynamic/testing';
import 'zone.js/dist/zone-testing';

declare const require: any;

// first, initialize the Angular testing environment.
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);
// then we find all the tests.
const context = require.context('./', true, /\.spec\.ts$/);
// and load the modules.
context.keys().map(context);
