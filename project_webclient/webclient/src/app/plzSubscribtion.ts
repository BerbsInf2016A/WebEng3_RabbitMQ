import { Subscription } from 'rxjs';

/**
 * A class for a "Postleitzahl" or zip code subscribtion.
*/
export class PlzSubscribtion {

    /**
     * The subscribtion.
     */
    subscription: Subscription;
    /**
     * The "Postleitzahl" or zip code the subsribtion is for.
     */
    plz: number;

    /**
     * Constructor for the PLZSubscribtion.
     *
     * @param PLZ The "Postleitzahl" or zip code the subsribtion is for.
     * @param subscribtion The subscribtion.
     */
    constructor(plz: number, subscribtion: Subscription) {
        this.plz = plz;
        this.subscription = subscribtion;
    }

}
