import { Component, OnInit } from '@angular/core';
import { Coupon } from 'src/app/models/Coupon';
import { CompanyService } from 'src/app/services/company.service';
import { DataUserService } from 'src/app/services/data-user.service';


@Component({
  selector: 'app-create-coupon',
  templateUrl: './create-coupon.component.html',
  styleUrls: ['./create-coupon.component.scss']
})
export class CreateCouponComponent implements OnInit {
  coupon :Coupon = new Coupon;
  constructor(public companyService: CompanyService , public dataUserService: DataUserService) { }

  ngOnInit() {

  }

  createCoupon(){
    console.log(this.coupon);
    this.companyService.createCoupon(this.coupon).subscribe(
      (next) => { alert("Successfully of created coupon") },
      (error) => { alert(error.error.message)}
    )

  }

  startDate(){
    return true;
    // if(this.coupon.startDate<this.coupon.endDate)return true;
    // else return false;
  }

  public endDate():boolean{
    if(this.coupon.endDate > new Date())  {return true;}
    else {return false;}
  }


}
