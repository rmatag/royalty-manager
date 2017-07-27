import {Http, Headers, Response, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/map";
import 'rxjs/add/operator/catch';
import {Injectable} from "@angular/core";
import {User} from "./User";
import {Router} from "@angular/router";
import 'rxjs/add/observable/of';

const HOST = "http://localhost:8080/royaltymanager";

interface Episode {
  id: string,
  name: string
};

export interface EpisodeGroup {
  studioId: string,
  studioName: string,
  episodes: Episode[]
};

export interface Payment {
  rightsOwner: string,
  royalty: string,
  viewings: number
};

@Injectable()
export class RoyaltyService {
  loggedUser: User;

  // Nice to have a wrapper class for the http in which the headers, host URL and the return of the json of the response
  // are done by it as well as catching the errors calling to the server
  constructor(private http: Http,
              private router: Router) {}

  public login(user: User): Observable<any> {
    this.loggedUser = user;
    return this.http.post(HOST + "/fakeLogin", {user: user.user, password: user.password}, this.getCredentials());
  }

  public getAllEpisodes(): Observable<EpisodeGroup[]> {

    return this.http.get(HOST + "/episodes", this.getCredentials())
      .map(response => response.json());

  }

  public getPayments(studioId): Observable<Payment> {

    return this.http.get(HOST + "/payments/" + studioId, this.getCredentials())
      .map(response => response.json());

  }

  public getAllPayments(): Observable<Payment[]> {

    return this.http.get(HOST + "/payments", this.getCredentials())
      .map(response => response.json());

  }

  public postView(episodeId: string): Observable<Response> {

    return this.http.post(HOST + "/viewings",
      {episode: episodeId, customer: this.loggedUser.user}, this.getCredentials());

  }

  public resetViews(): Observable<Response> {

    return this.http.post(HOST + "/reset", {}, this.getCredentials());

  }


  // Nice to have a session stored in the browser local storage to keep on logged in case of refresh the page
  private getCredentials(): RequestOptions {

    let options: RequestOptions = new RequestOptions();
    let headers: Headers = new Headers();

    if (this.loggedUser) {
      headers.append("Authorization", "Basic " + btoa(this.loggedUser.user + ":" + this.loggedUser.password));
      headers.append("Content-Type", "application/json");
    } else {
      this.redirectToLogin();
    }

    options.headers = headers;
    return options;
  }

  private redirectToLogin(): void {
    this.router.navigate(["login"]);
  }

}
