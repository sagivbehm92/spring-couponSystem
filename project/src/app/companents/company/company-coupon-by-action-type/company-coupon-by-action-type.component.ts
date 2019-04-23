import { Component, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/services/company.service';
import { ActivatedRoute } from '@angular/router';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { Coupon } from 'src/app/models/Coupon';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-company-coupon-by-action-type',
  templateUrl: './company-coupon-by-action-type.component.html',
  styleUrls: ['./company-coupon-by-action-type.component.scss']
})
export class CompanyCouponByActionTypeComponent implements OnInit {
  couponUpdate;
  data: Array<Coupon> = [];
  isUpdateCoupon: boolean = false;
  constructor(public companyService: CompanyService, public route: ActivatedRoute, public ngxSmartModalService: NgxSmartModalService) { }

  ngOnInit() {
    this.route.queryParams.subscribe(
      (params) => {
        this.companyService.actionType = params['actionType'];
        console.log(params);
        console.log(this.companyService.actionType)
      }
    )
    this.data = [];
  }


  getCompanyCouponsByPrice(price: number) {
    this.companyService.getCompanyCouponsByPrice(price).subscribe(
      (next) => {
        this.data = Object.values(next);
        console.log("by price")
        console.log(next)
        console.log(Object.values(next))
      },
      (error) => alert(error.error.message),
      () => console.log('finish'))
  }

  getCompanyCouponsByType(type: string) {
    console.log(type)
    this.companyService.getCompanyCouponsByType(type).subscribe(
      (next) => {
        this.data = Object.values(next);
      },
      (error) => alert(error.error.message),
      () => console.log('finish'))
  }



  getCompanyCouponsByDate(date: string) {
    this.companyService.getCompanyCouponsByDate(new Date(date).getTime()).subscribe(
      (next) => {
        this.data = Object.values(next);
        console.log("by date")
        console.log(next)
        console.log(Object.values(next))
      },
      (error) => alert(error.error.message),
      () => console.log('finish'))
  }

  daleteCoupon(id: number) {
    let isDelete = confirm("Are you sure you want to delete the coupon id : " + id);
    if (isDelete) {
      this.companyService.daleteCoupon(id).subscribe(
        (next) => console.log(next),
        (error) => alert(error.error.message)
      )
    }
  }

  updateCoupon(coupon: Coupon) {
    this.ngxSmartModalService.setModalData(coupon, 'myModal', true);
    this.isUpdateCoupon = true;
    this.ngxSmartModalService.getModal('myModal').open();
  }

  sendUpdate(id: number, titel: string, startDate: string, endDate: string, amount: number, price: number, message: string, image: string, couponType: string) {
     this.couponUpdate = new Coupon(id, titel, startDate, endDate, amount, couponType, message, price, image);
    console.log(this.couponUpdate);
    this.companyService.updateCoupon(new Coupon(id, titel, startDate, endDate, amount, couponType, message, price, image)).subscribe(
      (next) => { alert('update coupon successfully');this.refreshTheListCouponOnUpdate(this.couponUpdate); this.ngxSmartModalService.getModal('myModal').close(); },
      (error) => { alert(error.error.message) }
    )


  }

  refreshTheListCouponOnUpdate(coupon : Coupon){

    for (let index = 0; index < this.data.length; index++) {
     if(this.data[index].id==coupon.id){
      this.data[index].price=coupon.price
      this.data[index].endDate=coupon.endDate
     }
      
    }

  }
}
