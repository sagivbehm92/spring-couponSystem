import { Data } from "@angular/router";

export class Coupon{
    constructor(public  id?:number,
        public title?:string,
        public startDate?:any,
        public endDate?:any,
        public amount?:number,
        public type?:string,
        public message?:string,
        public price?:number,
        public image?:string,){ 
    }
    

    public getType():string{
        return this.type;
    }

}