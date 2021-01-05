import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MyReservationService } from '@core/services/my-reservation.service';


@NgModule({
	declarations: [],
	imports: [
		CommonModule,
		MatSnackBarModule
	],
	providers: [
		MyReservationService
	]
})
export class CoreModule { }