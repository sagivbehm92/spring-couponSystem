import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';
import { Coupon } from 'src/app/models/Coupon';
import { NgxSmartModalService } from 'ngx-smart-modal';
@Component({
  selector: 'app-company-coupons',
  templateUrl: './company-coupons.component.html',
  styleUrls: ['./company-coupons.component.scss']
})
export class CompanyCouponsComponent implements OnInit {
  data : Array<any>=[];
  coupons : Array<Coupon>=[];
  price: number;
  maxPrice: number= 0; 
  minPrice: number= 100;
  isUpdateCoupon :boolean = false;
  dateCouponUpdate:Date;
  constructor(public companyService: CompanyService , public ngxSmartModalService: NgxSmartModalService) { }

  ngOnInit() {  
    this.getCompanyCoupons();
  }



  getMaxPriceAndMinPrice(){

    for (let index = 0; index < this.coupons.length; index++) {
        if(this.coupons[index].price>this.maxPrice){
          this.maxPrice=this.coupons[index].price
        }
         if(this.coupons[index].price<this.minPrice){
          this.minPrice=this.coupons[index].price;
        }
      
    }
  }

  getCompanyCoupons() {
    this.companyService.getCompanyCoupons().subscribe(
      (next) => {
        this.coupons = this.data = Object.values(next);
        console.log(next) ; console.log( Object.values(next));
        this.getMaxPriceAndMinPrice();
      },
      //לגבי הארור אין לי רעיון איך להציג את זה בינתיים
      (error) => {alert(error.error.message) ; console.log(error)},
      () => console.log('finish'))
  }

  daleteCoupon(id:number){
    let isDelete = confirm("Are you sure you want to delete the coupon id : "+id);
    if(isDelete){
    this.companyService.daleteCoupon(id).subscribe(
      (next) => console.log(next) , 
      (error) =>  alert(error.error.message)
    )
    }
  }
  //בלחיצה על כפתורUPDATE 
  //נשנה את המימשק של המישתמש ונעביר אותו לUPDATE
  updateCoupon(coupon : Coupon){
    let number = coupon.endDate;
    this.dateCouponUpdate = new Date(number);
    this.ngxSmartModalService.setModalData(coupon, 'myModal' , true);
    this.isUpdateCoupon = true;
    this.ngxSmartModalService.getModal('myModal').open();
  }

  sendUpdate(id:number , titel:string , startDate:number , endDate:number , amount:number , price:number  , message:string , image:string  , couponType:string ){
    console.log(new Coupon(id , titel , startDate , endDate, amount , couponType , message , price , image ) )
    this.companyService.updateCoupon(new Coupon(id , titel , startDate , endDate, amount , couponType , message , price , image )).subscribe(
      (next) =>{alert('update coupon successfully') ; this.ngxSmartModalService.getModal('myModal').close();} ,
      (error) => { alert( error.error.message)}
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

}
