import {Component, OnInit} from '@angular/core';
import {PaymentsService} from "./PaymentsService";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  rightOwnerId: string;
  rightOwnerName: string;

  constructor(private paymentService: PaymentsService) {}

  ngOnInit(): void {
    this.paymentService.getPayments().subscribe((res) => {
      this.rightOwnerId  = res.rightOwnerId;
      this.rightOwnerName = res.rightOwner;
    }),
    (error => {
      console.log(error);
    });
  }
}
