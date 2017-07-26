import {Component} from "@angular/core";
import {User} from "./User";
import {RoyaltyService} from "./RoyaltyService";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  user: User = new User("", "");

  constructor(private royaltyService: RoyaltyService) {}

  login() {
    this.royaltyService.login(this.user);
  }

}
