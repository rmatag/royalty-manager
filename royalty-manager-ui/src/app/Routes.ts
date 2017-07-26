import {Routes} from "@angular/router";
import {RoyaltyComponent} from "./RoyaltyComponent";
import {RoyaltyResolver} from "./RoyaltyResolver";
import {LoginComponent} from "./LoginComponent";

export const routes: Routes = [
  {
    path: '',
    pathMatch: "full",
    redirectTo: 'login',

  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "royalty",
    component: RoyaltyComponent,
    resolve: {episodes: RoyaltyResolver}
  }

];
