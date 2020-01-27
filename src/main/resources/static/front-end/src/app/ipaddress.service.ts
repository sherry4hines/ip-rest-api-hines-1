import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError, tap, map, retry} from 'rxjs/operators';
import {IpAddress} from './ip-address.model';
import {CreateRequest} from './create-request.model';

@Injectable({
  providedIn: 'root'
})


export class IpaddressService {
  private baseUrl = 'http://localhost:8095/ip-address-mgmt/ip-addresses';
  private item: CreateRequest;
  private counter: number;

  private httpOptions = {
    headers: new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type',  'application/json')
      .set('Authorization', 'Basic  dXNlcjo4ZDI3NTA2Ny1iMWY3LTQ2ZWQtYTc2Ny1iMzkxMDA5MTRhMmM=')
  };

constructor(private http: HttpClient) { }

  getList(): Observable<IpAddress[]> {
    return this.http
      .get<IpAddress[]>(this.baseUrl, this.httpOptions)
      .pipe(
        retry(2)
      );
  }

  // Create new IP Addresses
  createItem(item): Observable<number> {
    return this.http.post<number>(this.baseUrl + '/create', JSON.stringify(item), this.httpOptions)
      .pipe(
        retry(2)
      );
  }

  // Create new IP Addresses
  releaseIP(item): Observable<IpAddress> {
    return this.http.post<IpAddress>(this.baseUrl + '/release', JSON.stringify(item), this.httpOptions)
      .pipe(
        retry(2)
      );
  }


  // Create new IP Addresses
  acquireIP(): Observable<IpAddress> {
    return this.http.get<IpAddress>(this.baseUrl + '/acquire', this.httpOptions)
      .pipe(
        retry(2)
      );
  }

  handleError<T>(serviceName = '', operation = 'operation', result = {} as T) {

    return (error: HttpErrorResponse): Observable<T> => {
      // Todo -> Send the error to remote logging infrastructure
      console.error(error); // log to console instead

      const message = (error.error instanceof ErrorEvent) ?
        error.error.message :
        `{error code: ${error.status}, body: "${error.message}"}`;

      // -> Return a safe result.
      return of( result );
    };
  }
}
