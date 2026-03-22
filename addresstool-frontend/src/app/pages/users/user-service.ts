import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Person } from '@shared/models/person';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = '/addresstool/api/person';
  private http = inject(HttpClient);

  constructor() {}

  getAllPersons(): Observable<Person[]> {
    return this.http.get<Person[]>(`${this.baseUrl}/all`);
  }

  getPagedPersons(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/allPaged`, {
      params: {
        pageNo: page.toString(),
        pageSize: size.toString(),
      },
    });
  }

  getById(id: number): Observable<Person> {
    return this.http.get<Person>(`${this.baseUrl}/${id}`);
  }

  save(person: Person): Observable<Person> {
    return this.http.post<Person>(this.baseUrl, person);
  }

  update(person: Person): Observable<void> {
    return this.http.put<void>(this.baseUrl, person);
  }

  delete(person: Person): Observable<void> {
    return this.http.delete<void>(this.baseUrl, { body: person });
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
