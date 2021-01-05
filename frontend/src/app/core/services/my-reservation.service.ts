import { isIdentifier } from '@angular/compiler';
import { Injectable} from '@angular/core';

import {MyReservation} from "@core/interfaces/my-reservation.interface";

@Injectable({
    providedIn: 'root'
})

export class MyReservationService {

    reservation: MyReservation [] = [
        { iid: 1, uid: -1, type: 'Smoking', details: 'Ide jön az első foglalás részletes leírása', timestamp: 160124188},
        { iid: 2, uid: -1, type: 'Outsider', details: 'Ide jön a második foglalás részletes leírása', timestamp: 160124188}
    ];
    constructor() {}

    public getReservations(): MyReservation[]{
        return this.reservation;  
    }
}