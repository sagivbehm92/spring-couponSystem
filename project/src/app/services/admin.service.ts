import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from '../models/company';
import { Customer } from '../models/customer';
import { LoginUser } from '../models/loginUser';
import { CanActivate, Router, CanActivateChild } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AdminService implements CanActivate , CanActivateChild {

  // urlCompany: string = 'http://localhost:8080/couponSystemProject/rest/company';//jersy
  // urlCustomer: string = 'http://localhost:8080/couponSystemProject/rest/customer';//jersy
  urlCompany: string = 'http://localhost:8080/couponSystemSpring/rest/company';//spring
  urlCustomer: string = 'http://localhost:8080/couponSystemSpring/rest/customer';//spring
  constructor(private httpclient: HttpClient, private router: Router) { }



  canActivate(): boolean {
    let loginUser: LoginUser = JSON.parse(sessionStorage.getItem("userLogin"));
    if (loginUser) {
      if (loginUser.type == "ADMIN") {
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
      if (loginUser.type == "ADMIN") {
        return true
      }
    }

      alert("you not have premission to this page")
      this.router.navigate(['**'])
      return false
  }


  //company
  getCompany(idcompany: number): Observable<Company> {
    return this.httpclient.get<Company>(this.urlCompany + "/" + idcompany, { withCredentials: true })
  }

  getAllCompany() {
    return this.httpclient.get(this.urlCompany + "/getAll", { withCredentials: true })
  }

  createCompany(company: Company) {
    return this.httpclient.post(this.urlCompany, company, { withCredentials: true })
  }

  deleteCompany(idcompany: string) {
    return this.httpclient.delete(this.urlCompany + "/" + idcompany, { withCredentials: true })
  }

  updateCompany(company: Company) {
    return this.httpclient.put(this.urlCompany, company, { withCredentials: true })
  }
  
  //customer

  getCustomer(idCustomer: number): Observable<Customer> {
    return this.httpclient.get<Customer>(this.urlCustomer + "/" + idCustomer, { withCredentials: true })
  }

  getAllCustomer(): Observable<Array<any>> {
    return this.httpclient.get<Array<any>>(this.urlCustomer + "/getAll", { withCredentials: true })
  }

  createCustomer(customer: Customer) {
    return this.httpclient.post(this.urlCustomer, customer, { withCredentials: true })
  }

  deleteCustomer(idCustomer: string) {
    return this.httpclient.delete(this.urlCustomer + "/" + idCustomer, { withCredentials: true })
  }

  updateCustomer(customer: Customer) {
    return this.httpclient.put(this.urlCustomer, customer, { withCredentials: true })
  }
}
