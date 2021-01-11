import { Component} from '@angular/core';
import { MyReservationService } from '@core/services/my-reservation.service';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-my-reservation',
  templateUrl: './my-reservation.component.html',
  styleUrls: ['./my-reservation.component.scss']
})

export class MyReservationComponent{
  public reservations = [];
  constructor(private reservedTable: MyReservationService, private toastr: ToastrService) { }

  ngOnInit(): void {
    this.reservations = [];
    this.reservedTable.getReservations().subscribe(data => this.reservations = data);
  }

  async deleteReservations(id: string){
    await this.reservedTable.deleteReservation(id).toPromise();
    this.reservations = [];
    this.reservedTable.getReservations().subscribe(data => this.reservations = data);
    this.toastr.success('Your reservation was successfully deleted');
  } 
}
