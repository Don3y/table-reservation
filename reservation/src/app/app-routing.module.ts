import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from '@core/guards/auth.guard';
import { AuthComponent } from './auth/auth.component';
import { MyReservationComponent } from './my-reservation/my-reservation.component';
import {NewReservationComponent} from './new-reservation/new-reservation.component'

import { PagenotfoundComponent } from './pagenotfound/pagenotfound.component';

const routes: Routes = [
	{ path: '', component: AuthComponent, },
	{ path: 'reservation/new-reservation', component: NewReservationComponent, canActivate: [AuthGuard] },
	{ path: 'reservation/my-reservation', component: MyReservationComponent, canActivate: [AuthGuard] },
	{ path: '404', component: PagenotfoundComponent },
	{ path: '**', redirectTo: '404', pathMatch: 'full' }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
