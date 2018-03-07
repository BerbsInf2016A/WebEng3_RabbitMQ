import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {StompConfig, StompService} from '@stomp/ng2-stompjs';

import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { stompConfig } from './stompconfig';
import { MDBBootstrapModule } from 'angular-bootstrap-md';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    MDBBootstrapModule.forRoot(),
    BrowserModule,
    FormsModule
  ],
  providers: [
    StompService,
    {
      provide: StompConfig,
      useValue: stompConfig
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
