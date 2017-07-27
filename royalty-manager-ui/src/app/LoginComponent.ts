import {Component} from "@angular/core";
import {User} from "./User";
import {RoyaltyService} from "./RoyaltyService";
import {Router} from "@angular/router";

import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/catch";
import 'rxjs/add/observable/empty';

@Component({
  selector: "login",
  templateUrl: "./LoginComponent.html",
  styleUrls: ["./LoginComponent.css"]
})
export class LoginComponent {
  constructor(private royaltyService: RoyaltyService,
              private router: Router) {
  }

  user: User = new User("", "");
  errorMsg: String;


  login() {
    return this.royaltyService.login(this.user)
      .catch((error) => {
        this.errorMsg = "The user or the password is incorrect. Please, try it again."
        return Observable.of()
      })
      .subscribe(() => {
        this.router.navigate(["/royalty"])

      });

  }
}
