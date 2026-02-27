import {computed, inject, Injectable, signal} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {HttpErrorService} from "@core/services/http-error.service";
import {SearchCriteria} from "@shared/models/searchCriteria";
import {catchError, map, Observable, of, switchMap} from "rxjs";
import {Person} from "@shared/models/person";
import {toObservable, toSignal} from "@angular/core/rxjs-interop";
import {Result} from "@core/entities/result";
import {shareReplay, tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root',
})
export class SearchService {

  private baseUrl = "/addresstool/api";
  private http = inject(HttpClient);
  private errorService = inject(HttpErrorService);

  constructor() { }

  // Search criteria signal
  private searchCriteria = signal<SearchCriteria>(undefined);

  public search(criteria: SearchCriteria) {
    this.searchCriteria.set(criteria);
  }

  public clearSearch() {
    this.searchCriteria.set(undefined);
  }

  private searchResult$ = toObservable(this.searchCriteria)
    .pipe(
      switchMap(criteria => {
        if (!criteria) {
          return of({data: [], error: undefined} as Result<Person[]>);
        }
        return this.searchPerson(criteria).pipe(
          tap(persons => console.log(persons)),
          map(persons => ({data: persons} as Result<Person[]>)),
          catchError(error => of({
            data: [],
            error: this.errorService.formatError(error)
          } as Result<Person[]>)),
        )
      }),
      shareReplay(1)
    );

  private searchResult = toSignal(this.searchResult$, {initialValue: {data: [], error: undefined}});
  persons = computed(() => this.searchResult()?.data);
  searchError = computed(() => this.searchResult()?.error);

  private searchPerson(searchCriteria: SearchCriteria): Observable<Person[]> {
    return this.http.post<Person[]>(`${this.baseUrl}/search`, searchCriteria);
  }
}
