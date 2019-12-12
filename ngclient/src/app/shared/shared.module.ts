import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ToastrModule } from 'ngx-toastr';
import { LoggerService } from './services/logger.service';
import { StorageService } from './services/storage.service';
import { WebService } from './services/web.service';
import { WINDOW_PROVIDERS } from './services/window.service';

/* expore all shared service as a module */
@NgModule({
  declarations: [],
  providers: [WINDOW_PROVIDERS, StorageService, LoggerService, WebService],
  imports: [CommonModule, HttpClientModule, ToastrModule.forRoot()],
  exports: []
})
export class SharedModule {}
