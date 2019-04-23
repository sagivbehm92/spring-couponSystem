import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './companents/login/login.component';
import { AdminComponent } from './companents/admin/admin.component';
import { ProductsComponent } from './companents/products/products.component';
import { CustomerComponent } from './companents/customer/customer.component';
import { CompanyComponent } from './companents/company/company.component';
import { GetAllCouponNotPurchaseComponent } from './companents/customer/get-all-coupon-not-purchase/get-all-coupon-not-purchase.component';
import { GetCustomerCouponComponent } from './companents/customer/get-customer-coupon/get-customer-coupon.component';
import { CustomerCouponByActionTypeComponent } from './companents/customer/customer-coupon-by-action-type/customer-coupon-by-action-type.component';
import { UpdateCustomerDetailsComponent } from './companents/customer/update-customer-details/update-customer-details.component';
import { CompanyCouponsComponent } from './companents/company/company-coupons/company-coupons.component';
import { CompanyCouponByActionTypeComponent } from './companents/company/company-coupon-by-action-type/company-coupon-by-action-type.component';
import { CreateCouponComponent } from './companents/company/create-coupon/create-coupon.component';
import { AddcompanyComponent } from './companents/admin/addcompany/addcompany.component';
import { GetAllCompaniesComponent } from './companents/admin/get-all-companies/get-all-companies.component';
import { GetAllCustomerComponent } from './companents/admin/get-all-customer/get-all-customer.component';
import { AddCustomerComponent } from './companents/admin/add-customer/add-customer.component';
import { UpdateCoumpanyComponent } from './companents/company/update-coumpany-password/update-coumpany.component';
import { UpdateEmailComponent } from './companents/company/update-email/update-email.component';
import { GetCompanyComponent } from './companents/admin/get-company/get-company.component';
import { GetCustomerComponent } from './companents/admin/get-customer/get-customer.component';
import { CustomerService } from './services/customer.service';
import { CompanyService } from './services/company.service';
import { AdminService } from './services/admin.service';
import { DataUserService } from './services/data-user.service';

const routes: Routes = [
  {
    path: '', redirectTo: '/products', pathMatch: "full"
  },
  { path: 'login', canActivate: [DataUserService], component: LoginComponent },
  { path: 'products', component: ProductsComponent },

  {
    //,canActivateChild: [AdminService]
    path: 'admin', canActivate: [AdminService], component: AdminComponent,
    children: [
      { path: 'addCompany', component: AddcompanyComponent },
      { path: 'addCustomer', component: AddCustomerComponent },
      { path: 'getAllCompanies', component: GetAllCompaniesComponent },
      { path: 'getAllCustomer', component: GetAllCustomerComponent },
      { path: 'getCompanyById', component: GetCompanyComponent },
      { path: 'getCustomerById', component: GetCustomerComponent },
      { path: '**', component: AdminComponent , pathMatch: "full" }
    ]
  },
  {
    //,canActivateChild: [CustomerService]
    path: 'customer', canActivate: [CustomerService], component: CustomerComponent,
    children: [
      { path: '', redirectTo: 'notPurchase', pathMatch: "full" },
      { path: "notPurchase", component: GetAllCouponNotPurchaseComponent },
      { path: "customerCoupon", component: GetCustomerCouponComponent },
      { path: "customerCouponsByActionType", component: CustomerCouponByActionTypeComponent },
      { path: "updateCustomer", component: UpdateCustomerDetailsComponent },
      { path: '**', component: CustomerComponent , pathMatch: "full" }
    ]
  },
  {
    //,canActivateChild: [CompanyService]
    path: 'company', canActivate: [CompanyService], component: CompanyComponent,
    children: [
      { path: '', redirectTo: 'companyCoupon', pathMatch: "full" },
      { path: "companyCoupon", component: CompanyCouponsComponent },
      { path: "companyCouponsByActionType", component: CompanyCouponByActionTypeComponent },
      { path: "createCoupon", component: CreateCouponComponent },
      { path: "updatePassword", component: UpdateCoumpanyComponent },
      { path: "updateEmail", component: UpdateEmailComponent },
      { path: '**', component: CompanyComponent , pathMatch: "full" }
    ]
  },

  { path: '**', component: ProductsComponent , pathMatch: "full" } // when the client write wrong URL

];

@NgModule({
  imports: [RouterModule.forRoot(routes , { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
