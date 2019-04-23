import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {
  // url: string ='http://localhost:8080/couponSystemProject/rest/logout';//jersy
  url: string ='http://localhost:8080/couponSystemSpring/rest/logout';//spring

  constructor(private httpClient:HttpClient) { }

  logout(){
    return this.httpClient.delete(this.url ,{withCredentials: true});
  }


}
