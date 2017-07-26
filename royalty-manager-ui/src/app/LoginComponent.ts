import {Component} from "@angular/core";
import {User} from "./User";
import {RoyaltyService} from "./RoyaltyService";

@Component({
  selector: 'login',
  templateUrl: './LoginComponent.html',
  styleUrls: ['./LoginComponent.css']
})
export class LoginComponent {

  user: User = new User("", "");

  constructor(private royaltyService: RoyaltyService) {}

  login() {
    this.royaltyService.login(this.user);
  }

}/**
 * Created by rmata on 7/26/17.
 */
