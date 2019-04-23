import { Component, OnInit } from '@angular/core';

import { DataUserService } from 'src/app/services/data-user.service';
import { CustomerService } from 'src/app/services/customer.service';
import { Coupon } from 'src/app/models/Coupon';

@Component({
  selector: 'app-get-customer-coupon',
  templateUrl: './get-customer-coupon.component.html',
  styleUrls: ['./get-customer-coupon.component.scss']
})
export class GetCustomerCouponComponent implements OnInit {
  data :Array<Coupon> = [];
  coupons :Array<Coupon> = [];
  maxPrice: number= 0; 
  price: number; 
  minPrice: number= 100;
  constructor(public customerService: CustomerService , public dataUserService: DataUserService,) { }

  ngOnInit() {

    this.getAllPurchaseCoupon();
  }


  setCouponByPrice(){
    console.log(this.price);
    this.coupons=[];
    for (let index = 0; index < this.data.length; index++) {
       if(this.data[index].price <= this.price ){
          this.coupons.push(this.data[index]);
       }
      
    }
  }


  getMaxPriceAndMinPrice(){

    for (let index = 0; index < this.coupons.length; index++) {
        if(this.coupons[index].price>this.maxPrice){
          this.maxPrice=this.coupons[index].price
        }
         if(this.data[index].price<this.minPrice){
          this.minPrice=this.coupons[index].price;
        }
      
    }
  }


  getAllPurchaseCoupon() {
    this.customerService.getAllPurchaseCoupon().subscribe(
      (next) => {
        console.log(Object.values(next)[0])
        Object.values(next)[0]
        this.coupons = this.data = next;
        this.getMaxPriceAndMinPrice();
      },
      (error) => { alert(error.error.message) } ,
    )
  
  }

}
