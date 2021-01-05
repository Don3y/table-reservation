import { Component} from '@angular/core';

import {MyReservationService} from '@core/services/my-reservation.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})

export class ReservationComponent{

  constructor(public is: MyReservationService) { }
}
