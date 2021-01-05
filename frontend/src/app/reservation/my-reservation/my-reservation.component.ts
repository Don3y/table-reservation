import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-my-reservation',
  templateUrl: './my-reservation.component.html',
  styleUrls: ['./my-reservation.component.scss']
})



export class MyReservationComponent implements OnInit {
  @Input() str: string;
  constructor() { }

  ngOnInit(): void {
  }

}
