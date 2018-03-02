import { Component } from '@angular/core';
import {StompService} from '@stomp/ng2-stompjs';
import {Message} from '@stomp/stompjs';
import 'rxjs/Rx';
import { PlzSubscribtion } from './plzSubscribtion';
import { Configuration } from './configuration';
import { WeatherData } from './weather/weatherData';
import { YoutubeData } from './youtube/youtubeData';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

/**
 * The AppComponent containing the content of the application.
 */
export class AppComponent {

  /**
   * The title of the app.
   */
  title = 'RabiitMQ Example';
  /**
   * The subscribtions for RabbitMQ.
   */
  subscriptions = [] as PlzSubscribtion[];
  // TODO: Messages is only for debugging and should be removed!
  messages = [] as String[];
  /**
   * The field containing a new plz to subsribe to.
   */
  subscribePLZ = '';
  /**
   * The field containing the plz to unsubscribe from.
   */
  unsubscribePLZ = '';
  /**
   * The weather data for the current subscribtions.
   */
  weatherData = [] as WeatherData[];
  /**
   * The youtube video data for the current subscribtions.
   */
  youtubeData = [] as YoutubeData[];

  /**
   * Constructor for the component.
   * 
   * @param _stompService The stomp service uses the STOMP protocol to communication with the RabbitMQ server.
   */
  constructor(private _stompService: StompService) {
  }

  /**
   * Notify all interested produces to provide information for the 
   * "Postleitzahl" or zip code.
   * @param plz The "Postleitzahl" or zip code.
   */
  notifyProducers(plz: number) {
    // Publish the message to the exchange.
    this._stompService.publish('/exchange/' + Configuration.sendExchangeName, plz.toString());
  }

  /**
   * Handle messages received from the RabbitMQ exchange.
   * 
   * @param msg_body The body of the message.
   */
  handleReceivedMessage(msg_body: String) {
    console.log(`Received: ${msg_body}`);
    this.messages.push(`Received: ${msg_body}`);

    // Handle a weather data package.
    if (msg_body.indexOf('"typeIdentifier" : "weather"') >= 0) {
      const obj = JSON.parse(msg_body.toString());
      const weatherData = new WeatherData(obj.plzString, obj.creationDate, obj.locationName, obj.temperature);
      // Remove old entry to avoid duplicates.
      this.weatherData = this.weatherData.filter(t => t.plz !== weatherData.plz);
      this.weatherData.push(weatherData);
      return;
    }
    // Handle a youtube video data package.
    if (msg_body.indexOf('"typeIdentifier" : "youtube"') >= 0) {
      const obj = JSON.parse(msg_body.toString());
      const youtubeData = new YoutubeData(obj.plzString, obj.creationDate, obj.locationName, obj.videoLink, obj.imageLink, obj.videoTitle);

      const existingVideo = this.youtubeData.find( t => t.videoLink === youtubeData.videoLink);
      if (existingVideo === undefined) {
        this.youtubeData.push(youtubeData);
      }
    }
  }

  /**
   * Subscribe to a "Postleitzahl" or zip code.
   *
   * @param plz The "Postleitzahl" or zip code to subscribe to.
   */
  public subscribeToPlz (plz: number) {
    // Check if we are already subscribed.
    const exists = this.subscriptions.filter(t => t.plz === plz);
    if (exists.length > 0) {
      this.subscribePLZ = '';
      return;
    }

    // Subscribe to the observable.
    const stom_observable = this._stompService.subscribe('/exchange/' + Configuration.receiveExchangeName + '/' + plz);

    // Subscribe to the messages and save the subscribtion.
    const subscribtion = stom_observable.map((message: Message) => {
      return message.body;
    }).subscribe((msg_body: string) => {
      this.handleReceivedMessage(msg_body);
    });
    this.subscriptions.push(new PlzSubscribtion(+plz, subscribtion));
    this.subscribePLZ = '';

    // Notify the producers, that we are interested in data for this plz.
    this.notifyProducers(plz);
  }

  /**
   * Unsubscribe from a "Postleitzahl" or zip code.
   *
   * @param plz The "Postleitzahl" or zip code to unsubscribe from.
   */
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


