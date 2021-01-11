import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CreateTable } from '@core/interfaces/create-reservation.interface';
import { NewReservationService } from '@core/services/new-reservation.service';
@Component({
  selector: 'app-new-reservation',
  templateUrl: './new-reservation.component.html',
  styleUrls: ['./new-reservation.component.scss']
})
export class NewReservationComponent implements OnInit {
  selectedValue: string;
  selectedTable: string;
  public freeTables = [];
  public tables = [];
  message: string;

  public reservation: CreateTable;
  public completed: string;
  constructor(private freeTable: NewReservationService, private table: NewReservationService, private toastr: ToastrService, private createTable: NewReservationService) {
  }

  displayedColumns: string[] = ['id', 'name', 'availability', 'type'];
  
  ngOnInit(): void {
    this.freeTables = [];
    this.tables = [];
    this.selectedTable = null;
    this.freeTable.getFreeTables().subscribe(data => 
      this.freeTables = data);
    this.table.getTables().subscribe(data => this.tables = data);
  }   
  
  async showSuccess() {
    if(this.selectedTable != null){
      this.reservation = {
        "resturanttable_name": this.selectedTable,
        "user_id": localStorage.getItem('id')
      };
      await this.createTable.setReservation(this.reservation).toPromise();
        this.toastr.success('Your reservation was successfully saved');
        this.ngOnInit();  
    }
    else{
      this.toastr.error('Choose a table', 'Error!');
    }
    
  }
}
