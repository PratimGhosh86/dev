import { isPlatformBrowser } from '@angular/common';
import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { GlobalConfig, ToastrService } from 'ngx-toastr';
import { environment } from './../../../environments/environment';

export enum LogLevel {
  INFO,
  WARN,
  ERROR,
  SUCCESS
}

@Injectable({
  providedIn: 'root'
})
export class LoggerService {
  toastrDefaultConfig: Partial<GlobalConfig> = {
    timeOut: 10000,
    positionClass: 'toast-top-right',
    preventDuplicates: true,
    closeButton: true,
    maxOpened: 1,
    autoDismiss: true,
    tapToDismiss: true,
    easing: 'ease-in'
  };

  constructor(
    @Inject(PLATFORM_ID) private platformId: any,
    private toastr: ToastrService
  ) {}
  /**
   *
   *
   * @param level the type of toast to display
   * @param title the title in the toast message
   * @param message the actual message to dislay
   * @param [options=this.toastrDefaultConfig] default configuration override
   */
  notify(
    level: LogLevel,
    title: string,
    message: string,
    options: Partial<GlobalConfig> = this.toastrDefaultConfig
  ): void {
    if (isPlatformBrowser(this.platformId))
      setTimeout(() => {
        switch (level) {
          case LogLevel.INFO:
            this.toastr.info(message, title, options);
            break;
          case LogLevel.WARN:
            this.toastr.warning(message, title, options);
            break;
          case LogLevel.ERROR:
            this.toastr.error(message, title, options);
            break;
          case LogLevel.SUCCESS:
            this.toastr.success(message, title, options);
            break;
          default:
            break;
        }
      });
  }
  /**
   * @param level the type of console log required
   * @param identifier any unique identifier
   * @param payload the object that needs to be logged
   */
  write(level: LogLevel, identifier: string, payload: any): void {
    if (isPlatformBrowser(this.platformId))
      if (!environment.production) {
        const msg = `[${identifier}] ${payload}`;
        switch (level) {
          case LogLevel.WARN:
          case LogLevel.ERROR:
            console.warn(msg);
            break;
          case LogLevel.INFO:
          case LogLevel.SUCCESS:
            console.log(msg);
            break;
          default:
            break;
        }
      }
  }
}
