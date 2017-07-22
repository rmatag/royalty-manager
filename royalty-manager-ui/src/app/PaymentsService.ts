import { Http } from "@angular/http";
import { Observable } from "rxjs/Observable";
import 'rxjs/add/operator/map'
import {Injectable} from "@angular/core";

import { Headers } from "@angular/http";



export interface Payment {
  rightOwnerId: string;
  rightOwner: string;
}

@Injectable()
export class PaymentsService {
  constructor(private http: Http) {}

  getPayments(): Observable<Payment> {


    return this.http.get("http://localhost:8080/royaltymanager/payments").map((res) => {
      return {rightOwnerId: res.json().rightOwnnerId, rightOwner: res.json().rightOwner};
    });

  }
}
