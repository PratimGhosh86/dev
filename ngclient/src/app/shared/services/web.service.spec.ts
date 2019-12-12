import { TestBed } from '@angular/core/testing';
import { WebService } from './web.service';

describe('WebService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WebService = TestBed.get(WebService);
    expect(service).toBeTruthy();
  });
});
