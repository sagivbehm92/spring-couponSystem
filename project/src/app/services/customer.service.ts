import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { PasswordBeans } from '../models/passwordBeans';
import { CanActivate, Router, CanActivateChild } from '@angular/router';
import { LoginUser } from '../models/loginUser';

@Injectable({
  providedIn: 'root'
})
export class CustomerService implements CanActivate , CanActivateChild  {
  
  actionType: string;
  // url: string = "http://localhost:8080/couponSystemProject/rest/coupon";//jersy
  // urlUpdate: string = "http://localhost:8080/couponSystemProject/rest/customer/byCustomer";//dersy
  url: string = "http://localhost:8080/couponSystemSpring/rest/coupon";//spring
  urlUpdate: string = "http://localhost:8080/couponSystemSpring/rest/customer/byCustomer";//spring

  constructor(private httpClient: HttpClient, private router: Router) { }


  canActivate(): boolean {
    let loginUser: LoginUser = JSON.parse(sessionStorage.getItem("userLogin"));
    if (loginUser) {
      if (loginUser.type == "CUSTOMER") {
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
      if (loginUser.type == "CUSTOMER") {
        return true
      }
    }
      alert("you not have premission to this page")
      this.router.navigate(['**'])
      return false
  }


  getAllCouponNotPurchase(): Observable<Array<any>> {
    return this.httpClient.get<Array<any>>(this.url + "/couponNotPurchase", { withCredentials: true });
  }

  purchaseCoupon(idCoupon) {
    return this.httpClient.get(this.url + "/purchaseCoupon/" + idCoupon, { withCredentials: true , responseType: "text" });
  }


  getAllPurchaseCoupon() {
    return this.httpClient.get<Array<any>>(this.url + "/customerCoupons", { withCredentials: true });
  }


  customerCouponsByType(type: string) {
    return this.httpClient.get<Array<any>>(this.url + "/customerCouponsByType?type=" + type, { withCredentials: true });
  }

  customerCouponsByPrice(price: number) {
    return this.httpClient.get<Array<any>>(this.url + "/customerCouponsByPrice?price=" + price, { withCredentials: true });
  }

  getCouponById(idCoupon: number) {
    return this.httpClient.get(this.url + "/" + idCoupon, { withCredentials: true })
  }

  customerUpdate(passwordBeans: PasswordBeans) {
    return this.httpClient.put(this.urlUpdate, passwordBeans, { withCredentials: true, responseType: 'json' }
    );
  }

}
