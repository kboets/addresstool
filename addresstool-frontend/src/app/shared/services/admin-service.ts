import {computed, inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {HttpErrorService} from "@core/services/http-error.service";
import {map, Observable, of} from "rxjs";
import {Result} from "@core/entities/result";
import {catchError, tap} from "rxjs/operators";
import {toSignal} from "@angular/core/rxjs-interop";
import {Admin} from "@shared/models/admin";

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private baseUrl = '/addresstool/api/admin';
  private http = inject(HttpClient);
  private errorService = inject(HttpErrorService);

  // retrieve version
  private versionResult$ = this.getVersion().pipe(
    tap((response) => console.log('maven version:', response.mavenVersion)),
    map((response) => ({ data: response.mavenVersion }) as Result<string>),
    catchError((error) =>
      of({
        data: undefined,
        error: this.errorService.formatError(error),
      } as Result<string>),
    ),
  );

  private versionResult = toSignal(this.versionResult$, { initialValue: { data: '', error: undefined } });
  version = computed(() => this.versionResult()?.data);
  versionError = computed(() => this.versionResult()?.error);

  private getVersion(): Observable<Admin> {
    return this.http.get<Admin>(`${this.baseUrl}/currentVersion`);
  }
}
