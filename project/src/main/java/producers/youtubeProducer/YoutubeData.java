package producers.youtubeProducer;

import producers.BaseDTO;

public class YoutubeData extends BaseDTO {

    private static String youtubeLink;

    public YoutubeData(int plz) {
        super(plz, "youtube");
    }


    public String getYoutubeLink() {
        return youtubeLink;
    }
}
