package app.rest;

import app.domain.derivatives.WatchInfoByUser;
import app.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations/{username}")
public class RecommendationResource {
    RecommendationService recommendationService;

    public RecommendationResource(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/movies/friends/{level}")
    public List<WatchInfoByUser> getFriendsFilms(@PathVariable String username, @PathVariable int level) {
        return recommendationService.getFriendsFilms(username, level);
    }

    @GetMapping("/movies/country")
    public List<WatchInfoByUser> getCountryFilms(@PathVariable String username) {
        return recommendationService.getCountryFilms(username);
    }

    @GetMapping("/movies/advise")
    public List<WatchInfoByUser> getAdviseFilms(@PathVariable String username) {
        return recommendationService.getAdviseFilms(username);
    }
}
