import {Component} from "@angular/core";
import {User} from "./User";
import {RoyaltyService} from "./RoyaltyService";

@Component({
  selector: 'login',
  templateUrl: './LoginComponent.html',
  styleUrls: ['./LoginComponent.css']
})
export class LoginComponent {
  constructor(private royaltyService: RoyaltyService) {}

  user: User = new User("", "");
  errorMsg: String;


  login() {
    this.royaltyService.login(this.user);
  }

}
