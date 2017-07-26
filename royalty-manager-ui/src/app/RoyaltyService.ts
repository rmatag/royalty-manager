import {Http, Headers} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/map";
import {Injectable} from "@angular/core";
import {User} from "./User";
import {Router} from "@angular/router";

@Injectable()
export class RoyaltyService {
  loggedUser: User;

  constructor(private http: Http,
              private router: Router) {}

  public login(user: User) {
    this.loggedUser = user;
    this.router.navigate(["royalty"]);

  }

  public getAllEpisodes(): Observable<any> {

    let credentials = this.getCredentials();
    return this.http.get("http://localhost:8080/royaltymanager/episodes", {headers: credentials});

  }

  private getCredentials(): Headers {
    let headers: Headers = new Headers();
    headers.append("Authorization", "Basic " + btoa(this.loggedUser.user + ":" + this.loggedUser.password));

    return headers;
  }
}
