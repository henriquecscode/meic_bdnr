package app.rest;

import app.domain.derivatives.*;
import app.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsResource {
    AnalyticsService analyticsService;

    public AnalyticsResource(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }


    @GetMapping("/workers/country")
    public List<WorkerCountryInfo> getSameCountry() {
        return analyticsService.getSameCountry();
    }

    @GetMapping("/awards/genre/{limit}")
    public List<GenreAwards> getGenreWithMostAwards(@PathVariable int limit) {
        return analyticsService.getGenreWithMostAwards(limit);
    }

    @GetMapping("/awards/worker/{limit}")
    public List<WorkerAwards> getWorkerWithMostAwards(@PathVariable int limit) {
        return analyticsService.getWorkerWithMostAwards(limit);
    }

    @GetMapping("/awards/country/{limit}")
    public List<CountryAwards> getCountryWithMostAwards(@PathVariable int limit) {
        return analyticsService.getCountryWithMostAwards(limit);
    }

    @GetMapping("/friends/series/complete")
    public List<WatchedSeriesInfo> getSeriesWatchedByUsers(){
        return analyticsService.getSeriesWatchedByUsers();
    }

    @GetMapping("/friends/{username}/series/complete")
    public List<WatchedSeriesInfo> getSeriesWatchedByFriends(@PathVariable String username){
        return analyticsService.getSeriesWatchedByFriends(username);
    }

}
