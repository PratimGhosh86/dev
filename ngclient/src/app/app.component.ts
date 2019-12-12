import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  OnInit
} from '@angular/core';
import { setTheme } from 'ngx-bootstrap/utils';
import { LoggerService, LogLevel } from './shared/services/logger.service';
import { StorageService } from './shared/services/storage.service';
import { WebService } from './shared/services/web.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  constructor(
    private changeDetector: ChangeDetectorRef,
    private logger: LoggerService,
    private storage: StorageService,
    private web: WebService
  ) {
    setTheme('bs4');
    // sample usage
    logger.write(LogLevel.INFO, this.constructor.name, '<- component name');
    logger.notify(LogLevel.SUCCESS, this.constructor.name, '↑ component name');
  }

  ngOnInit(): void {
    // called after the constructor, initializing input properties,
    // and the first call to ngOnChanges
  }

  ngAfterViewInit(): void {
    // called after ngAfterContentInit when the component's view has
    // been initialized. Applies to components only.
    this.changeDetector.detectChanges();
  }
}
