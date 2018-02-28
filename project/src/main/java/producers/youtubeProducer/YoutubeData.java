package producers.youtubeProducer;

import producers.BaseDTO;

public class YoutubeData extends BaseDTO {

    private static String youtubeLink;

    public YoutubeData(String plz) {
        super(plz, "youtube");
    }


    public String getYoutubeLink() {
        return youtubeLink;
    }
}
