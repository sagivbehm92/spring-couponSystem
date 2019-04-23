import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginUser } from '../models/loginUser';
import { observable, Observable } from 'rxjs';
import { Router, CanActivateChild, CanActivate } from '@angular/router';
import { Coupon } from '../models/Coupon';

@Injectable({
  providedIn: 'root'
})
export class DataUserService implements CanActivate {
  // urlAllCoupons : string = "http://localhost:8080/couponSystemProject/rest/coupon/getall";//jersy
  urlAllCoupons : string = "http://localhost:8080/couponSystemSpring/rest/coupon/getall";//spring


  url = 'http://localhost:8080/couponSystemProject/rest/checklogin';
  isHome: boolean = true;
  isLogin: boolean = false;
  userName: string;
  userId: number;
  type: string;

  constructor(private httpClient: HttpClient, private router: Router) {
    //if the client refresh the page 
    let loginUser: LoginUser = JSON.parse(sessionStorage.getItem("userLogin"));
    if (loginUser) {
      this.setIsLogin(true);
      this.setUserName(loginUser.name);
      this.setType(loginUser.type);
      this.setUserId(loginUser.id)
      console.log(this)
    }
  }


  public canActivate(): boolean {

    if (this.isLogin == true) {
      alert("you already login")
      this.router.navigate(["**"]);
      return false;
    } else {
      return true;
    }
  }

  canActivateChild(): boolean {
    if (this.isLogin == true) {
      // this.doSwitch()
      return true;
    } else {
      //this.router.navigate([""]);
      return false;
    }

  }



  public getAllCoupons():Observable<Coupon[]>{
    return this.httpClient.get<Coupon[]>(this.urlAllCoupons ,{withCredentials: true,});
  }




  public checkLogin():void {
    this.httpClient.get<LoginUser>(this.url, { withCredentials: true }).subscribe(
      (next) => {
        sessionStorage.setItem("userLogin", JSON.stringify(next));
        this.setIsLogin(true);
        this.setUserName(next.name);
        this.setType(next.type);
        this.setUserId(next.id)
        this.isHome=true;
        this.routeClient();
      },
      (error)=>{console.log(error)}
    )
  }

  public setUserId(userId: number) {
    this.userId = userId;
  }
  public getUserId(): number {
    return this.userId;
  }

  public getIsLogin(): boolean {
    return this.isLogin;
  }


  public setIsLogin(isLogin: boolean) {
    this.isLogin = isLogin;
  }

  public getUserName(): string {
    return this.userName;
  }

  public setUserName(userName: string) {
    this.userName = userName;
  }

  public getType(): string {
    return this.type;
  }

  public setType(type: string): void {
    this.type = type;
  }


  public getIsHome(): boolean {
    return this.isHome;
  }

  public setIsHome(isHome: boolean) {
    this.isHome = isHome;
  }

  routeClient() {

    switch (this.type) {
      case 'ADMIN':
        this.router.navigate(["/admin"])
        break;

      case 'COMPANY':
        this.router.navigate(["/company"])
        break;

      case 'CUSTOMER':
        this.router.navigate(["/customer"])
        break;

      default:
        break;
    }
  }





}
