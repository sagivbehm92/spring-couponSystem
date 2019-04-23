import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule , FormsModule } from '@angular/forms';
import { NgxSmartModalModule } from 'ngx-smart-modal';
import { AppRoutingModule } from './app-routing.module';


import { AppComponent } from './companents/layout/app.component';
import { ProductsComponent } from './companents/products/products.component';
import { LoginComponent } from './companents/login/login.component';
import { AdminComponent } from './companents/admin/admin.component';
import { LogoutComponent } from './companents/logout/logout.component';
import { CustomerComponent } from './companents/customer/customer.component';
import { CompanyComponent } from './companents/company/company.component';
import { AddcompanyComponent } from './companents/admin/addcompany/addcompany.component';
import { GetAllCouponNotPurchaseComponent } from './companents/customer/get-all-coupon-not-purchase/get-all-coupon-not-purchase.component';
import { GetCustomerCouponComponent } from './companents/customer/get-customer-coupon/get-customer-coupon.component';
import { CustomerCouponByActionTypeComponent } from './companents/customer/customer-coupon-by-action-type/customer-coupon-by-action-type.component';
import { UpdateCustomerDetailsComponent } from './companents/customer/update-customer-details/update-customer-details.component';
import { CompanyCouponsComponent } from './companents/company/company-coupons/company-coupons.component';
import { CompanyCouponByActionTypeComponent } from './companents/company/company-coupon-by-action-type/company-coupon-by-action-type.component';
import { CreateCouponComponent } from './companents/company/create-coupon/create-coupon.component';
import { GetAllCompaniesComponent } from './companents/admin/get-all-companies/get-all-companies.component';
import { GetAllCustomerComponent } from './companents/admin/get-all-customer/get-all-customer.component';
import { AddCustomerComponent } from './companents/admin/add-customer/add-customer.component';
import { UpdateCoumpanyComponent } from './companents/company/update-coumpany-password/update-coumpany.component';
import { UpdateEmailComponent } from './companents/company/update-email/update-email.component';
import { GetCompanyComponent } from './companents/admin/get-company/get-company.component';
import { GetCustomerComponent } from './companents/admin/get-customer/get-customer.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminComponent,
    ProductsComponent,
    LogoutComponent,
    CustomerComponent,
    CompanyComponent,
    AddcompanyComponent,
    GetAllCouponNotPurchaseComponent,
    GetCustomerCouponComponent,
    CustomerCouponByActionTypeComponent,
    UpdateCustomerDetailsComponent,
    CompanyCouponsComponent,
    CompanyCouponByActionTypeComponent,
    CreateCouponComponent,
    UpdateCoumpanyComponent,
    GetAllCompaniesComponent,
    GetAllCustomerComponent,
    AddCustomerComponent,
    UpdateEmailComponent,
    GetCompanyComponent,
    GetCustomerComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgbModule.forRoot(),
    NgbModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgxSmartModalModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
