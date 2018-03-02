package producers.youtubeProducer;

import producers.BaseDTO;

public class YoutubeDataDto extends BaseDTO {

    /**
     * The title of the video.
     */
    private final String videoTitle;

    /**
     * The link to the video.
     */
    private final String videoLink;

    /**
     * The link to the image.
     */
    private final String imageLink;

    /**
     * Constructor for YoutubeDataDto class.
     *
     * @param plz          The "Postleitzahl" or zip code of the dto.
     * @param locationName The name of the location this dto is for.
     * @param videoTitle   The title of the video.
     * @param videoLink    The link to the video.
     * @param imageLink    The link to the previewImage.
     */
    public YoutubeDataDto(int plz, String locationName, String videoTitle, String videoLink, String imageLink) {
        super(plz, "youtube", locationName);
        this.videoTitle = videoTitle;
        this.videoLink = videoLink;
        this.imageLink = imageLink;
    }

    /**
     * Get the title of the video.
     *
     * @return The title of the video.
     */
    public String getVideoTitle() {
        return videoTitle;
    }

    /**
     * Get the link to the video.
     *
     * @return The link to the video.
     */
    public String getVideoLink() {
        return videoLink;
    }

    /**
     * Get the link to the image.
     *
     * @return The link to the image.
     */
    public String getImageLink() {
        return imageLink;
    }
}
