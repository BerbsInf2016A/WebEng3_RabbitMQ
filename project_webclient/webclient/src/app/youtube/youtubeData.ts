/** A class containing youtube video data. */
export class YoutubeData {
    /**
     * The title of the video.
     */
    videoTitle: String;
    /**
     * The link to the preview image.
     */
    imageLink: String;
    /**
     * The link to the video.
     */
    videoLink: String;
    /**
     * The name of the location this data is for.
     */
    locationName: String;
    /**
     * The creation date of this data.
     */
    creationDate: String;
    /**
     * The "Postleitzahl" or zip code for this data.
     */
    plz: String;

    /**
     * Constructor for the youtube data class.
     * 
     * @param plz The "Postleitzahl" or zip code for this data.
     * @param creationDate The creation date of this data.
     * @param locationName The name of the location this data is for.
     * @param videoLink The link to the video.
     * @param imageLink The link to the preview image.
     * @param videoTitle The title of the video.
     */
    constructor(plz: String, creationDate: String, locationName: String, videoLink: String, imageLink: String, videoTitle: String ){
        this.plz = plz;
        this.creationDate = creationDate;
        this.locationName = locationName;
        this.videoLink = videoLink;
        this.imageLink = imageLink;
        this.videoTitle = videoTitle;
    }
}
