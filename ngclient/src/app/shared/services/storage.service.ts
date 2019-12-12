import { isPlatformServer } from '@angular/common';
import { Inject, Injectable, InjectionToken, PLATFORM_ID } from '@angular/core';

export const LOCAL_STORAGE = new InjectionToken<Storage>('Local Storage', {
  providedIn: 'root',
  factory: () => localStorage
});

export const SESSION_STORAGE = new InjectionToken<Storage>('Session Storage', {
  providedIn: 'root',
  factory: () => sessionStorage
});

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor(
    @Inject(PLATFORM_ID) private platformId: any,
    @Inject(LOCAL_STORAGE) private local: Storage,
    @Inject(SESSION_STORAGE) private session: Storage
  ) {}

  /**
   * @param key the identifier against which the value will be stored
   * @param value the value that needs to be stored
   * @param [hash=false] should the above value be hashed
   * @param [persist=false] should the above value be persisted between session
   * @returns true is data was stored, false otherwise
   */
  saveToStore(
    key: string,
    value: any,
    willHash = false,
    willPersist = false
  ): boolean {
    if (isPlatformServer(this.platformId)) return false;

    const toStore = willHash ? btoa(value) : value;
    if (willPersist) this.local.setItem(key, toStore);
    else this.session.setItem(key, toStore);

    return true;
  }

  /**
   * @param key the identifier against which the value will be retrieved
   * @param [hash=false] was the stored value hashed
   * @param [persist=false] was the stored value persisted
   * @returns the stored value againss the provided key
   */
  retrieveFromStore(key: string, wasHashed = false, wasPersisted = false): any {
    if (isPlatformServer(this.platformId)) return undefined;
    const toReturn = wasPersisted
      ? this.local.getItem(key)
      : this.session.getItem(key);

    return wasHashed ? atob(toReturn) : toReturn;
  }
  /**
   *
   *
   * @param key the identifier which will be removed
   * @param [wasPersisted=false] was the stored value persisted
   * @returns true is data was removed, false otherwise
   */
  removeFromStore(key: string, wasPersisted = false): boolean {
    if (isPlatformServer(this.platformId)) return false;
    wasPersisted ? this.local.removeItem(key) : this.session.removeItem(key);

    return true;
  }

  /**
   * @param [clearLocal=true] clear local storage
   * @param [clearSession=true] clear session storage
   * @returns true is data was cleared, false otherwise
   */
  clear(clearLocal = true, clearSession = true): boolean {
    if (isPlatformServer(this.platformId)) return false;

    if (clearLocal) this.local.clear();
    if (clearSession) this.session.clear();

    return true;
  }
}
