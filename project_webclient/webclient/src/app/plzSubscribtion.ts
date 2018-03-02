import { Subscription } from "rxjs";

export class PlzSubscribtion {

    subscription: Subscription;
    plz: number;
    constructor(PLZ : number, subscribtion : Subscription) {
        this.plz = PLZ;
        this.subscription = subscribtion;
    }

}