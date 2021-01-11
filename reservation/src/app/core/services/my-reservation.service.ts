import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { ReservedTable } from 'src/app/core/interfaces/reserved.interface';
import { baseUrl } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
  })

  export class MyReservationService {
    httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': '',
          'Access-Control-Allow-Origin': 'http://reservationtracker.herokuapp.com',
          'Access-Control-Allow-Methods': 'GET, POST, OPTIONS, PUT, PATCH, DELETE'
        })
      };

    constructor(private http: HttpClient){}
    getReservations(){
        return this.http.get<ReservedTable[]>(`${baseUrl}/reservation/${localStorage.getItem('id')}`);
    }
    deleteReservation(delReservation: string){
      return this.http.delete<ReservedTable[]>(`${baseUrl}/reservation/${delReservation}`);
    }
}