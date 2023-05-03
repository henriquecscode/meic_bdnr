package app.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import app.domain.Genre;
import app.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreResource {

    private final Logger log = LoggerFactory.getLogger(GenreResource.class);

    private final GenreService genreService;

    public GenreResource(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("")
    public List<Genre> list() {
        return genreService.findAll();
    }
}
