import { Component } from '@angular/core';
import {StompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import 'rxjs/Rx';
import { PlzSubscribtion } from './plzSubscribtion';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  title = 'app';
  subscriptions = [] as PlzSubscribtion[];
  messages = [] as String[];
  currentPLZ = "";

  constructor(private _stompService: StompService) {
   }
  
  

  public subscribeToPlz (plz: String) {
    let exists = this.subscriptions.filter(t => t.plz == plz);
    if(exists.length > 0){
      return;
    }
    let stom_observable = this._stompService.subscribe('/exchange/PLZExchange/' + plz);

    let subscribtion = stom_observable.map((message: Message) => {
      return message.body;
    }).subscribe((msg_body: string) => {
      console.log(`Received: ${msg_body}`);
      this.messages.push(`Received: ${msg_body}`);
    });
    this.subscriptions.push(new PlzSubscribtion(plz, subscribtion));
    this.currentPLZ = "";
  }

  public unsubscribeFromPlz (plz: String) {
    let subscribtion = this.subscriptions.find(x => x.plz == plz);
    if (subscribtion != undefined) {
      subscribtion.subscription.unsubscribe();
      this.subscriptions = this.subscriptions.filter(t => t != subscribtion);
    }
    this.currentPLZ = "";
  }  
}


