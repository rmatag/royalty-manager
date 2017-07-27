import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {EpisodeGroup, Payment, RoyaltyService} from "./RoyaltyService";
import {isNullOrUndefined} from "util";

@Component({
  selector: 'royalty',
  templateUrl: './RoyaltyComponent.html',
  styleUrls: ['./RoyaltyComponent.css']
})



export class RoyaltyComponent implements OnInit {
  episodesGroup: EpisodeGroup[];
  payments: Payment[];
  paymentsByOwnerId: any;


  constructor(private route: ActivatedRoute,
              private royaltyService: RoyaltyService) {}

  ngOnInit(): void {
    this.episodesGroup = this.route.snapshot.data["episodesGroup"];
    this.payments = new Array();
    this.paymentsByOwnerId;
  }

  view(event) {
    this.royaltyService.postView(event.target.id).subscribe(() => {
        // Nice to show a Successful notification
    });
  }

  resetViews() {
    this.royaltyService.resetViews().subscribe(() => {
      this.payments = [];
      this.paymentsByOwnerId = null;
    });
  }

  getPayments(event) {
    this.royaltyService.getPayments(event.target.id).subscribe((res) => {
        this.paymentsByOwnerId = res;
    });
  }

  getAllPayments() {
    this.royaltyService.getAllPayments().subscribe((res) => {
      this.payments = res;
    });
  }

  existPayments() : boolean {
    return this.payments.length > 0;
  }

  existPaymentsByOwnerId(): boolean {
    return this.paymentsByOwnerId;
  }
}
