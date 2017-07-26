import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {EpisodeGroup, RoyaltyService} from "./RoyaltyService";
import {Observable} from "rxjs/Observable";
import "rxjs/add/observable/throw";
import 'rxjs/add/operator/catch';



@Injectable()
export class RoyaltyResolver implements Resolve<any> {

  constructor(private royaltyService: RoyaltyService,
              private router: Router) {}
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<EpisodeGroup[]> {
      return this.royaltyService.getAllEpisodes()
        .catch((error) => {
          this.handleError(error);
          return Observable.throw("UnAuthorized");
        });
  }

  private handleError(error: any): void {
    if (error.status !== 401) {
      this.router.navigate(["error"]);
    }
  }

}
