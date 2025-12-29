import {computed, inject, Injectable, signal} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {filter, map, Observable, of, switchMap} from "rxjs";
import {toObservable, toSignal} from "@angular/core/rxjs-interop";
import {catchError, shareReplay, tap} from "rxjs/operators";
import {HttpErrorService} from "@core/services/http-error.service";
import {Result} from "@core/entities/result";

@Injectable({
  providedIn: 'root',
})
export class AddressService {

  private baseUrl = "/addresstool/api";
  private http = inject(HttpClient);
  private errorService = inject(HttpErrorService);

  constructor() { }

  // selected postal code signal
  selectedPostalCode = signal<number>(undefined);
  // set the selected postal code when given
  public postalCodeSelected(zipCode: number) {
    console.log("postal code selected: " + zipCode);
    this.selectedPostalCode.set(zipCode);
  }

  // get cities from zipcode
  private cityNamesResult$ = toObservable(this.selectedPostalCode)
    .pipe(
      filter(zipCode => !!zipCode),
      tap(zipCode => console.log("before the call to the backend: " + zipCode)),
      switchMap(zipCode => this.getCities(zipCode)),
      shareReplay(1),
      catchError(error => of({
        data: undefined,
        error: this.errorService.formatError(error)
      } as Result<string[]>)),
      map(cities => ({data: cities} as Result<string[]>))
    );

  private cityNamesResult = toSignal(this.cityNamesResult$, {initialValue: {data: []}});
  cityNames = computed(() => this.cityNamesResult()?.data);
  cityNamesError = computed(() => this.cityNamesResult()?.error);


  private getCities(zipCode: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/cityNamesByPostalCode/${zipCode}`)
      .pipe(
        tap(cities => console.log(cities)));
  }



}
