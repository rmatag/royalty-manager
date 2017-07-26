import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { RoyaltyService } from "./RoyaltyService";
import {RouterModule} from "@angular/router";
import {routes} from "./Routes";
import {RoyaltyComponent} from "./RoyaltyComponent";

import { AppComponent } from './app.component';
import {RoyaltyResolver} from "./RoyaltyResolver";
import {LoginComponent} from "./LoginComponent";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RoyaltyComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(routes)
  ],
  providers: [
    RoyaltyService,
    RoyaltyResolver
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
