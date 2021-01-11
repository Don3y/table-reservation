import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { FreeTable } from '../interfaces/free-table.interface';
import { CreateTable } from '@core/interfaces/create-reservation.interface';
import { Table } from '../interfaces/table.interface';
import { baseUrl } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
  })

  export class NewReservationService {
    httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
          'Authorization': '',
          'Access-Control-Allow-Origin': 'http://reservationtracker.herokuapp.com',
          'Access-Control-Allow-Methods': 'GET, POST, OPTIONS, PUT, PATCH, DELETE'
        })
      };
    constructor(private http: HttpClient){}

    getFreeTables(){
        return this.http.get<FreeTable[]>(`${baseUrl}/tables/free`);
    }
    setReservation(table: CreateTable){
        return this.http.post<CreateTable[]>(`${baseUrl}/reservation`, table, this.httpOptions);
    }
    getTables(){
        return this.http.get<Table[]>(`${baseUrl}/tables`);
    }
}