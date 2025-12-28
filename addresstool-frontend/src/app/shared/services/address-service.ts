import {inject, Injectable, signal} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {filter, Observable, switchMap} from "rxjs";
import {toObservable, toSignal} from "@angular/core/rxjs-interop";
import {shareReplay, tap} from "rxjs/operators";
import {HttpErrorService} from "@core/services/http-error.service";

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
  private cities$ = toObservable(this.selectedPostalCode)
    .pipe(
      filter(zipCode => !!zipCode),
      tap(zipCode => console.log("before the call to the backend: " + zipCode)),
      switchMap(zipCode => this.getCities(zipCode)),
      shareReplay(1)
    );

  cities = toSignal(this.cities$, {initialValue: []});

  private getCities(zipCode: number): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/cityNamesByPostalCode/${zipCode}`)
      .pipe(
        tap(cities => console.log(cities)));
  }



}
