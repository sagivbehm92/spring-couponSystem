import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Coupon } from '../models/Coupon';
import { Company } from '../models/company';
import { PasswordBeans } from '../models/passwordBeans';
import { LoginUser } from '../models/loginUser';
import { Router, CanActivate , CanActivateChild } from '@angular/router';
import { DataUserService } from './data-user.service';


@Injectable({
  providedIn: 'root'
})
export class CompanyService implements CanActivate , CanActivateChild  {

  actionType: string;
  // url: string = "http://localhost:8080/couponSystemProject/rest/coupon";//jersy
  // urlUpdate: string = 'http://localhost:8080/couponSystemProject/rest/company';//jersy
  url: string = "http://localhost:8080/couponSystemSpring/rest/coupon";//spring
  urlUpdate: string = 'http://localhost:8080/couponSystemSpring/rest/company';//spring
  constructor(private httpClient: HttpClient, private router: Router, private dataUserService: DataUserService) { }

  canActivate(): boolean {
    let loginUser: LoginUser = JSON.parse(sessionStorage.getItem("userLogin"));
    if (loginUser) {
      if (loginUser.type == "COMPANY") {
        return true
      }
    }
   
      alert("you not have premission to this page")
      this.router.navigate(['**'])
      return false
  }


  canActivateChild(): boolean {
    let loginUser: LoginUser = JSON.parse(sessionStorage.getItem("userLogin"));
    if (loginUser) {
      if (loginUser.type == "COMPANY") {
        return true
      }
    }
   
      alert("you not have premission to this page")
      this.router.navigate(['**'])
      return false
  }

  getCompanyCoupons(): Observable<Array<any>> {
    return this.httpClient.get<Array<any>>(this.url + "/companyCoupons", { withCredentials: true })
  }

  daleteCoupon(id: number) {
    return this.httpClient.delete(this.url + "/" + id, { withCredentials: true })
  }

  createCoupon(coupon: Coupon) {
    return this.httpClient.post(this.url + "/create", coupon, { withCredentials: true })
  }
  getCompanyCouponsByPrice(price: number): Observable<Array<Coupon>> {
    return this.httpClient.get<Array<Coupon>>(this.url + "/companyCouponsByPrice?price=" + price, { withCredentials: true })
  }
  getCompanyCouponsByDate(date: number): Observable<Array<Coupon>> {
    return this.httpClient.get<Array<Coupon>>(this.url + "/companyCouponsByDate?date=" + date, { withCredentials: true })
  }

  getCompanyCouponsByType(type: string): Observable<Array<Coupon>> {
    return this.httpClient.get<Array<Coupon>>(this.url + "/companyCouponsByType?type=" + type, { withCredentials: true })
  }

  updateCoupon(coupon: Coupon) {
    return this.httpClient.put(this.url, coupon, { withCredentials: true })
  }

  getActionType(): string {
    return this.actionType;
  }

  updatePasswordCompany(passwordBeans: PasswordBeans) {
    return this.httpClient.put(this.urlUpdate + "/byCompanyPassword", passwordBeans, { withCredentials: true, responseType: 'json' }
    );
  }
  updateEmaildCompany(company: Company) {
    return this.httpClient.put(this.urlUpdate, company, { withCredentials: true, responseType: 'json' }
    );
  }

}
