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

  constructor(private _stompService: StompService) {
   }
  
  

  public subscribeToPlz (plz: String) {
    let stom_observable = this._stompService.subscribe('/exchange/PLZExchange/' + plz);

    let subscribtion = stom_observable.map((message: Message) => {
      return message.body;
    }).subscribe((msg_body: string) => {
      console.log(`Received: ${msg_body}`);
    });
    this.subscriptions.push(new PlzSubscribtion(plz, subscribtion));
  }

  public unsubscribeFromPlz (plz: String) {
    let subscribtion = this.subscriptions.find(x => x.plz == plz);
    if (subscribtion != undefined) {
      subscribtion.subscription.unsubscribe();
      this.subscriptions = this.subscriptions.filter(t => t != subscribtion);
    }
  }
}


