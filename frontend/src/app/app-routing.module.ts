import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ReservationComponent } from './reservation/reservation.component';
import { SettingsComponent } from './settings/settings.component';
import { AuthComponent } from './auth/auth.component';

const routes: Routes = [
  { path: '', component: AuthComponent},
  {path: 'reservation/new-reservation', component: SettingsComponent},
  {path: 'reservation/my-reservation', component: ReservationComponent},
  {path: 'settings', component: SettingsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
