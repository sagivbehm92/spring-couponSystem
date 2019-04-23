import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { LoginUser } from '../models/loginUser';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  // url: string = 'http://localhost:8080/couponSystemProject/rest/login';//jersy
  url: string = 'http://localhost:8080/couponSystemSpring/rest/login';//spring
  isLogin = false;
  constructor(private httpClient: HttpClient) { }

  login(user: LoginUser):Observable<number> {
   return this.httpClient.post<number>(this.url , user , {withCredentials: true})
  }

  public getIsLogin(): boolean {
    return this.isLogin;
  }

  public setIsLogin(isLogin: boolean) {
    this.isLogin = isLogin;
  }

}