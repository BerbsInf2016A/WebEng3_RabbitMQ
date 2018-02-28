import { Component } from '@angular/core';
import {StompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import 'rxjs/Rx';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  constructor(private _stompService: StompService) {
    let stomp_subscription = this._stompService.subscribe('/exchange/PLZExchange/74706');

    stomp_subscription.map((message: Message) => {
      return message.body;
    }).subscribe((msg_body: string) => {
      console.log(`Received: ${msg_body}`);
    });
   }
  
  title = 'app';

  public subsribeToPlz (plz: String) {
    let stomp_subscription = this._stompService.subscribe('/exchange/PLZExchange/' + plz);

    stomp_subscription.map((message: Message) => {
      return message.body;
    }).subscribe((msg_body: string) => {
      console.log(`Received: ${msg_body}`);
    });
  }
}


