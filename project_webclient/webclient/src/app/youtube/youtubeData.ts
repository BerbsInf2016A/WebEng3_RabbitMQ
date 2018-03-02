export class YoutubeData {
    videoTitle: String;
    imageLink: String;
    videoLink: String;
    locationName: String;
    creationDate: String;
    plz: number;
    typeIdentifier: String;

    constructor(plz: number, creationDate: String, locationName: String, videoLink: String, imageLink: String, videoTitle: String ){
        this.plz = plz;
        this.creationDate = creationDate;
        this.locationName = locationName;
        this.videoLink = videoLink;
        this.imageLink = imageLink;
        this.videoTitle = videoTitle;
    }
}
