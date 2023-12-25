package dathkt.controller;

import dathkt.service.YouTubeAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class YouTubeSearchController {

    private final YouTubeAPIService youTubeAPIService;

    @Autowired
    public YouTubeSearchController(YouTubeAPIService youTubeAPIService) {

        this.youTubeAPIService = youTubeAPIService;
    }
    @GetMapping("/search")
    public String index(){

        return "search/index";
    }

    @PostMapping ("/search")
    public String search(@RequestParam("searchTerm") String searchTerm, Model model) {
        String searchResult = youTubeAPIService.searchVideos(searchTerm);
        // Xử lý kết quả tìm kiếm nếu cần và đưa vào model để trả về giao diện
        model.addAttribute("searchResult", searchResult);
        return "search/result";
    }
}