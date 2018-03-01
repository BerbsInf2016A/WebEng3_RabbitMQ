import { Subscription } from "rxjs";

export class PlzSubscribtion {

    subscription: Subscription;
    plz: String;
    constructor(PLZ : String, subscribtion : Subscription) {
        this.plz = PLZ;
        this.subscription = subscribtion;
    }

}