package app.rest;

import app.domain.derivatives.MovieInfo;
import app.domain.derivatives.Search;
import app.service.SearchService;
import app.service.TitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import app.domain.Title;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class TitleResource {

    private final Logger log = LoggerFactory.getLogger(TitleResource.class);

    private final TitleService titleService;
    private final SearchService searchService;

    public TitleResource(TitleService titleService, SearchService searchService) {
        this.titleService = titleService;
        this.searchService = searchService;
    }

    @GetMapping("/{id}")
    public MovieInfo findByTitle(@PathVariable String id) {
        MovieInfo movieInfo = titleService.getInfoById(id);
        return movieInfo;
    }

    @PostMapping("/search")
    public List<Title> findBy(@RequestBody Search body) {
        List<Title> titles = new ArrayList<>();

        boolean doFilter = false;
        if (body.getTitle() != null) {
            titles = searchService.findByTitle(body.getTitle());
            doFilter = true;
        }

        if (body.getGenre() != null) {
            if (doFilter) {
                titles = titleService.findByGenre(body.getGenre(), titles);
            } else {
                titles = titleService.findByGenre(body.getGenre());
            }
        }

        if (body.getYear() != null) {
            if (doFilter) {
                titles = titleService.findByYear(body.getYear(), titles);
            } else {
                titles = titleService.findByYear(body.getYear());
            }
        }


        return titles;
    }

    @GetMapping("")
    public List<Title> list() {
        return titleService.findAll();
    }
}
