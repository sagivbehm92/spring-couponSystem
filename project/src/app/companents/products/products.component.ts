import { Component, OnInit } from '@angular/core';
import { DataUserService } from 'src/app/services/data-user.service';
import { Coupon } from 'src/app/models/Coupon';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  img = `https://images.pexels.com/photos/236636/pexels-photo-236636.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940`;
  products: Array<Coupon>;
  searchCoupons: Array<Coupon>;
  constructor(public dataUserService: DataUserService) { }

  ngOnInit() {

    
    if (this.dataUserService.isLogin == false) {
      
      let rememberMe: boolean = JSON.parse(localStorage.getItem("rememberMe"))
      if (rememberMe == true) {
        // בדיקה מול השרת האם הלקוח עדיין מחובר ואם כן העברה לדף ייעודי 
        this.dataUserService.checkLogin();
      }
    }


    this.dataUserService.getAllCoupons().subscribe(
      (next) => {
      this.searchCoupons = this.products = Object.values(next), console.log(Object.values(next))
      },
      (error) => { console.log(error) ; 

        if(error.error.message){
          alert(error.error.message) 
        }else{
          alert("We are renovating the app for you Please try later")
        }
      },
      () => { console.log('finish') }
    )
  }

  reset(){
    this.searchCoupons = this.products;
  }


  SearchById(id) {
    console.log("the id" + id)
    if (id!="") {
    this.searchCoupons = [];
      for (let index = 0; index < this.products.length; index++) {
        if (this.products[index].id <= id) {
          this.searchCoupons.push(this.products[index])
        }

      }
    }else{
      this.searchCoupons=this.products;
    }

  }

}

