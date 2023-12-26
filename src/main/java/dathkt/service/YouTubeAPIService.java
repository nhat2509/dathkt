package dathkt.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YouTubeAPIService {

    private final String apiKey = "AIzaSyCfzHoXPxm4UgUcmWfRoBSXYj4GlzAugiU";

    public Object searchVideos(String searchTerm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + searchTerm + "&key=" + apiKey;
        return restTemplate.getForObject(url, Object.class);
    }
}