import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {StompConfig, StompService} from '@stomp/ng2-stompjs';

import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { stompConfig } from './stompconfig';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    NgbModule.forRoot(),
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
