package producers.youtubeProducer;

import producers.BaseDTO;

public class YoutubeData extends BaseDTO {


    private final String videoTitle;
    private final String videoLink;
    private final String imageLink;

    public YoutubeData(int plz, String locationName, String videoTitle, String videoLink, String imageLink)
    {
        super(plz, "youtube", locationName);
        this.videoTitle = videoTitle;
        this.videoLink = videoLink;
        this.imageLink = imageLink;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public String getImageLink() {
        return imageLink;
    }
}
