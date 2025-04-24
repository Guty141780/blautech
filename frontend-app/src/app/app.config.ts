import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { LoggerModule, NgxLoggerLevel } from 'ngx-logger';

import { appRoutes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(appRoutes),
    provideHttpClient(),
    importProvidersFrom(
      LoggerModule.forRoot({
        level: NgxLoggerLevel.DEBUG,          
        serverLogLevel: NgxLoggerLevel.OFF 
      })
    )
  ]
};