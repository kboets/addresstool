import {computed, inject, Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {debounceTime, distinctUntilChanged, map, merge, Observable, of, switchMap} from 'rxjs';
import {toObservable, toSignal} from '@angular/core/rxjs-interop';
import {catchError, shareReplay} from 'rxjs/operators';
import {HttpErrorService} from '@core/services/http-error.service';
import {Result} from '@core/entities/result';

@Injectable({
  providedIn: 'root',
})
export class AddressService {
  private baseUrl = '/addresstool/api';
  private http = inject(HttpClient);
  private errorService = inject(HttpErrorService);

  constructor() {}

  // ** retrieve city names via postal code ** //

  // selected postal code signal
  selectedPostalCode = signal<number>(undefined);

  public postalCodeSelected(zipCode: number) {
    this.selectedPostalCode.set(zipCode);
  }
  public resetPostalCode() {
    this.selectedPostalCode.set(undefined);
  }

  // retrieve all cities from zipcode
  private cityNamesResult$ = toObservable(this.selectedPostalCode).pipe(
    debounceTime(500),
    distinctUntilChanged(),
    switchMap((zipCode) => {
      if (!zipCode) {
        return of({ data: [], error: undefined } as Result<string[]>);
      }
      return this.getCities(zipCode).pipe(
        map((cities) => ({ data: cities }) as Result<string[]>),
        catchError((error) =>
          of({
            data: undefined,
            error: this.errorService.formatError(error),
          } as Result<string[]>),
        ),
      );
    }),
    shareReplay(1),
  );

  private cityNamesResult = toSignal(this.cityNamesResult$, { initialValue: { data: [], error: undefined } });
  cityNames = computed(() => this.cityNamesResult()?.data);
  cityNamesError = computed(() => this.cityNamesResult()?.error);

  private getCities(zipCode: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/cityNamesByPostalCode/${zipCode}`);
  }

  // ****** retrieve postal code via city name ****** //

  //selected city name
  selectedCityName = signal<string>(undefined);
  public cityNameSelected(cityName: string) {
    this.selectedCityName.set(cityName);
  }
  public resetCityName() {
    this.selectedCityName.set(undefined);
  }

  //get postal code from city name
  private postalCodeResult$ = toObservable(this.selectedCityName).pipe(
    debounceTime(500),
    distinctUntilChanged(),
    switchMap((cityName) => {
      if (!cityName) {
        return of({ data: undefined, error: undefined } as Result<string>);
      }
      return this.getPostalCode(cityName).pipe(
        map((postalCode) => ({ data: postalCode }) as Result<string>),
        catchError((error) =>
          of({
            data: undefined,
            error: this.errorService.formatError(error),
          } as Result<string>),
        ),
      );
    }),
    shareReplay(1),
  );
  private postalCodeResult = toSignal(this.postalCodeResult$, { initialValue: { data: '', error: undefined } });
  postalCode = computed(() => this.postalCodeResult().data);
  postalCodeError = computed(() => this.postalCodeResult()?.error);

  private getPostalCode(cityName: string): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/postalCodeByCityName/${cityName}`);
  }

  // ****** retrieve street name with postal code or city name ****** //
  private streetResetTick = signal(0);

  public resetStreetNames() {
    this.streetResetTick.update((v) => v + 1); // force an emission so streets become []
  }

  private streetsResult$ = merge(
    toObservable(this.selectedPostalCode).pipe(map((zip) => ({ zip }))),
    toObservable(this.selectedCityName).pipe(map((city) => ({ city }))),
    toObservable(this.streetResetTick).pipe(map(() => ({ reset: true as const }))),
  ).pipe(
    debounceTime(300),
    switchMap((criteria: { zip?: number; city?: string; reset?: true }) => {
      if (criteria.reset) {
        return of([]);
      } else if (criteria.zip) {
        return this.getStreetNamesByPostalCode(criteria.zip);
      } else if (criteria.city) {
        return this.getStreetNamesByCityName(criteria.city);
      }
      return of([]);
    }),
    map((streets) => ({ data: streets }) as Result<string[]>),
    catchError((error) =>
      of({
        data: undefined,
        error: this.errorService.formatError(error),
      } as Result<string[]>),
    ),
    shareReplay(1),
  );

  private streetsResult = toSignal(this.streetsResult$, { initialValue: { data: [], error: undefined } });
  streets = computed(() => this.streetsResult()?.data);
  streetsError = computed(() => this.streetsResult()?.error);

  private getStreetNamesByPostalCode(postalCode: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/streetByPostalCode/${postalCode}`);
  }
  private getStreetNamesByCityName(cityName: string): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/streetByCity/${cityName}`);
  }
}
