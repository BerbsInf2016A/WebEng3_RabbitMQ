import { Component } from '@angular/core';
import {StompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import 'rxjs/Rx';
import { PlzSubscribtion } from './plzSubscribtion';
import { configuration } from './configuration';
import { WeatherData } from './weather/weatherData';
import { YoutubeData } from './youtube/youtubeData';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  title = 'app';
  subscriptions = [] as PlzSubscribtion[];
  // TODO: Messages is only for debugging and should be removed!
  messages = [] as String[];
  subscribePLZ = '';
  unsubscribePLZ = '';
  weatherData = [] as WeatherData[];
  youtubeData = [] as YoutubeData[];

  constructor(private _stompService: StompService) {
  }


  notifyBackend(plz: number) {
    this._stompService.publish('/exchange/' + configuration.sendExchangeName, plz.toString());
  }

  handleReceivedMessage(msg_body: String) {
    console.log(`Received: ${msg_body}`);
    this.messages.push(`Received: ${msg_body}`);

    if (msg_body.indexOf('"typeIdentifier" : "weather"') >= 0) {
      const obj = JSON.parse(msg_body.toString());
      const weatherData = new WeatherData(obj.plzString, obj.creationDate, obj.locationName, obj.temperature);
      // Remove old entry to avoid duplicates.
      this.weatherData = this.weatherData.filter(t => t.plz !== weatherData.plz);
      this.weatherData.push(weatherData);
    }

    if (msg_body.indexOf('"typeIdentifier" : "youtube"') >= 0) {
      const obj = JSON.parse(msg_body.toString());
      const youtubeData = new YoutubeData(obj.plzString, obj.creationDate, obj.locationName, obj.videoLink, obj.imageLink, obj.videoTitle);

      const existingVideo = this.youtubeData.find( t => t.videoLink === youtubeData.videoLink);
      if(existingVideo === undefined) {
        this.youtubeData.push(youtubeData);
      }
    }

  }
  public subscribeToPlz (plz: number) {
    const exists = this.subscriptions.filter(t => t.plz === plz);
    if (exists.length > 0) {
      return;
    }
    const stom_observable = this._stompService.subscribe('/exchange/' + configuration.receiveExchangeName + '/' + plz);

    const subscribtion = stom_observable.map((message: Message) => {
      return message.body;
    }).subscribe((msg_body: string) => {
      this.handleReceivedMessage(msg_body);
    });
    this.subscriptions.push(new PlzSubscribtion(+plz, subscribtion));
    this.subscribePLZ = '';

    this.notifyBackend(plz);
  }

  public unsubscribeFromPlz (plz: number) {
    // Remove subscribtion
    const subscribtion = this.subscriptions.find(x => x.plz === plz);
    if (subscribtion !== undefined) {
      subscribtion.subscription.unsubscribe();
      this.subscriptions = this.subscriptions.filter(t => t !== subscribtion);
    }

    // Remove already received data
    this.weatherData = this.weatherData.filter(t => t.plz.toString() !== plz.toString());
    this.youtubeData = this.youtubeData.filter(t => t.plz.toString() !== plz.toString());

    this.unsubscribePLZ = '';
  }
}


