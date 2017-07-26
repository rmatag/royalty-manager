
import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {RoyaltyService} from "./RoyaltyService";


@Injectable()
export class RoyaltyResolver implements Resolve<any> {

  constructor(private royaltyService: RoyaltyService) {}
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      return this.royaltyService.getAllEpisodes();
  }


}
