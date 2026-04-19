import {inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {HttpErrorService} from "@core/services/http-error.service";
import {Observable} from "rxjs";
import {Country} from "@shared/models/country";

@Injectable({
  providedIn: 'root',
})
export class CountryService {

  private baseUrl = '/addresstool/api';
  private http = inject(HttpClient);
  private errorService = inject(HttpErrorService);

  constructor() {}

  // retrieve all countries
  public getEuropeanCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(`${this.baseUrl}/getEuropeanCodes`);
  }
}
