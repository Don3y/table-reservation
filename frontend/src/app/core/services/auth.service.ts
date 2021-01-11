import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';

import { NotificationService } from '@core/services/notification.service';

import { User } from '@core/interfaces/user.interface';

import { baseUrl } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLogin$ = new BehaviorSubject<boolean>(this.hasToken());

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': '',
      'Accept': 'application/json'
    })
  };

  constructor(
    private http: HttpClient,
    private router: Router,
    private ns: NotificationService
  ) { }

  isLoggedIn(): Observable<boolean> {
    return this.isLogin$.asObservable();
  }

  register(user: User): void {
    this.http.post<User>(`${baseUrl}/users/registry`, user, this.httpOptions).subscribe(
      data => {
        this.ns.show('Successful registrartion!');
      },
      error => {
        this.ns.show('Error! Registration failed!');
        console.error(error);
      }
    );
  }

  login(user: User): void {
    this.http.post<User>(`${baseUrl}/users/singin`, user, this.httpOptions).subscribe(
      data => {
        localStorage.setItem('id', data['id']);
        localStorage.setItem('token', data['token']);
        this.isLogin$.next(true);
        this.ns.show('Successful login!');
        this.router.navigate(['/reservation/new-reservation']);
      },
      error => {
        this.ns.show('Error! Login failed!');
        console.error(error);
      }
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('id');
    this.isLogin$.next(false);
    this.ns.show('Successful logout!');
    this.router.navigate(['/']);
  }

  protected hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

}