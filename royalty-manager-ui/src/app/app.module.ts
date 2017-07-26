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
import {ErrorComponent} from "./ErrorComponent";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RoyaltyComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    CommonModule,
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
