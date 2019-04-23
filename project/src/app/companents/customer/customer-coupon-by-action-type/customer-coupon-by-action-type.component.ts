import { Component, OnInit } from '@angular/core';
import { CustomerService } from 'src/app/services/customer.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-customer-coupon-by-action-type',
  templateUrl: './customer-coupon-by-action-type.component.html',
  styleUrls: ['./customer-coupon-by-action-type.component.scss']
})
export class CustomerCouponByActionTypeComponent implements OnInit {
  data: Array<any> = [];
  actionType: string;
  constructor(public customerService: CustomerService, public route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams.subscribe(
      (params) => {
        this.customerService.actionType = params['actionType'];
        console.log(params);
        console.log(this.customerService.actionType)
      }
      )
      this.data = [];
  }


  customerCouponsByPrice(price: number) {
    this.data = [];
    console.log(Object.values(price))
    this.customerService.customerCouponsByPrice(price).subscribe(
      (next) => {
        console.log(Object.values(next))
        this.data = next;
      },
      (error) => { alert(error.error.message) } ,
    )
  }



  getCouponById(idCoupon: number) {
    this.data = [];
    this.customerService.getCouponById(idCoupon).subscribe(
      (next) => { this.data.push(next) },
      (error) => { alert(error.error.message) },

    )
  }


  customerCouponsByType(type: string) {
    this.data = [];
    this.customerService.customerCouponsByType(type).subscribe(
      (next) => {
        console.log(Object.values(next))
        this.data = next;
      },
      (error) => { alert(error.error.message) } ,
    )
  }




}
