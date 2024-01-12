package dathkt.controller;

import dathkt.service.YouTubeAPIService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class YouTubeSearchController {

    private final YouTubeAPIService youTubeAPIService;

    @Autowired
    public YouTubeSearchController(YouTubeAPIService youTubeAPIService) {
        this.youTubeAPIService = youTubeAPIService;
    }

    @GetMapping("/search")
    public String index() {

        return "search/index";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchTerm") String searchTerm, Model model) {

        Object searchResult = youTubeAPIService.searchVideos(searchTerm);
        model.addAttribute("searchResult", searchResult);
        return "search/result";
    }
    @GetMapping("/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadVideo(@RequestParam("videoUrl") String videoUrl, HttpServletResponse response) throws IOException {
        System.out.println("Download request received for: " + videoUrl);
        try {
            URL url = new URL(videoUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream videoStream = connection.getInputStream();

            byte[] videoBytes = videoStream.readAllBytes();
            System.out.println("Video Size: " + videoBytes.length);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "video.mp4");
            headers.setContentLength(videoBytes.length);
            headers.set("Content-Type", "video/mp4");

            videoStream.close();
            connection.disconnect();

            return new ResponseEntity<>(videoBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}