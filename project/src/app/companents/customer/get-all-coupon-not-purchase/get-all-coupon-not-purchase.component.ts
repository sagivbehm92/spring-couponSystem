import { Component, OnInit } from '@angular/core';

import { Coupon } from 'src/app/models/Coupon';
import { CustomerService } from 'src/app/services/customer.service';

@Component({
  selector: 'app-get-all-coupon-not-purchase',
  templateUrl: './get-all-coupon-not-purchase.component.html',
  styleUrls: ['./get-all-coupon-not-purchase.component.scss']
})
export class GetAllCouponNotPurchaseComponent implements OnInit {
  data : Array<any>=[];
  coupons : Array<Coupon>=[];
  maxPrice: number= 0; 
  price: number; 
  minPrice: number= 100;
  constructor(public customerService: CustomerService) { }

  ngOnInit() {
   this.getAllCouponNotPurchase();
  }

  getAllCouponNotPurchase(){
  
    this.customerService.getAllCouponNotPurchase().subscribe(
      (next) => {
        // console.log(Object.values(next)[0])
        if (next instanceof Array) {
          this.coupons = this.data = next;
        } else {
          this.data.push(next)
          this.coupons.push(next)
        }
        this.getMaxPriceAndMinPrice()
  
      },
      (error) => { alert(error.error) } ,
    )
  }

  purchaseCoupon(idCoupon) {
    this.customerService.purchaseCoupon(idCoupon).subscribe(
      (next) => { alert(next), console.log(next); this.getAllCouponNotPurchase() },
      (error) => { alert(error.error.message), this.getAllCouponNotPurchase()}
    )
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



}

