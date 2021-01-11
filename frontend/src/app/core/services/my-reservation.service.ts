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
          'Authorization': ''
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