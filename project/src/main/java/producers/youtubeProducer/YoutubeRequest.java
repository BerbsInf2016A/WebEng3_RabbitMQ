package producers.youtubeProducer;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import producers.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubeRequest {
    /**
     * The youtube api client.
     */
    private static YouTube youtube;

    /**
     * Request youtube data.
     *
     * @param keyword         The keyword to search for.
     * @param numberOfResults Limit the number or retrieved results.
     * @param plz             The plz to generate the dtos for.
     * @return A list of YoutubeDataDto.
     */
    public List<YoutubeDataDto> request(String keyword, long numberOfResults, String plz) {
        List<YoutubeDataDto> results = new ArrayList<>();

        try {

            if (youtube == null) {
                // This object is used to make YouTube Data API requests. The last
                // argument is required, but since we don't need anything
                // initialized when the HttpRequest is initialized, we override
                // the interface and provide a no-op function.
                youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) {
                    }
                }).setApplicationName("youtube-cmdline-search-sample").build();
            }

            String queryTerm = keyword;
            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            String apiKey = Configuration.instance.youtubeApiKey;
            search.setKey(apiKey);
            search.setQ(queryTerm);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/high/url)");
            search.setMaxResults(numberOfResults);
            search.setOrder("date");

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                for (SearchResult entry : searchResultList) {
                    results.add(this.generateDto(entry, plz, keyword));
                }
                return results;
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    /**
     * Generate a YoutubeDataDto for the given source data.
     *
     * @param source  The search result als source element.
     * @param plz     The plz to create the dto for.
     * @param keyword The keyword or location name for the dto.
     * @return The created dto.
     */
    private YoutubeDataDto generateDto(SearchResult source, String plz, String keyword) {
        ResourceId id = source.getId();
        String videoTitle = source.getSnippet().getTitle();
        String videoLink = Configuration.instance.youtubeVideoURLPrefix + id.getVideoId();
        String imageLink = source.getSnippet().getThumbnails().getHigh().getUrl();
        return new YoutubeDataDto(plz, keyword, videoTitle, videoLink, imageLink);
    }
}
