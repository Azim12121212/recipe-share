import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8080'

  constructor(private http: HttpClient) { }

  authSubject = new BehaviorSubject<any>({
    user:null
  })

  login(userData:any):Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/auth/signin`, userData)
  }

  register(userData:any):Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/auth/signup`, userData)
  }

  getUserProfile():Observable<any> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem("jwt")}`
    })
    return this.http.get<any>(`${this.baseUrl}/users/profile`, {headers}).pipe(
      tap((user)=>{
        const currentState = this.authSubject.value;
        this.authSubject.next({...currentState, user});
      })
    );
  }

  logout() {
    localStorage.clear()
    this.authSubject.next({})
  }
}
