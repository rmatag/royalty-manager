import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'royalty',
  templateUrl: './RoyaltyComponent.html',
  styleUrls: ['./RoyaltyComponent.css']
})



export class RoyaltyComponent implements OnInit {
  episodes: any;
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.episodes  = this.route.snapshot.data["episodes"];

  }


}
