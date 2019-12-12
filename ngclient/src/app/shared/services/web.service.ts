import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { WINDOW } from './window.service';

export enum WsType {
  GET,
  PUT,
  POST,
  DELETE
}

@Injectable({
  providedIn: 'root'
})
export class WebService {
  constructor(
    @Inject(WINDOW) private window: Window,
    private http: HttpClient
  ) {}
}
