package xyz.dassiorleando.springbootorientdb.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.dassiorleando.springbootorientdb.domain.Title;
import xyz.dassiorleando.springbootorientdb.domain.derivatives.MovieInfo;
import xyz.dassiorleando.springbootorientdb.service.TitleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
public class TitleResource {

    private final Logger log = LoggerFactory.getLogger(TitleResource.class);

    private final TitleService titleService;

    public TitleResource(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping("/{id}")
    public MovieInfo findByTitle(@PathVariable String id) {
        MovieInfo movieInfo = titleService.getInfoById(id);
        return movieInfo;
    }
//    @GetMapping("/{id}")
//    public Title findByTitle(@PathVariable String id) {
//        log.debug("We just get the title of id {} one more time", id);
//        Title title = titleService.findById(id);
//
//        return title;
//    }

    @GetMapping("")
    public List<Title> list() {
        return titleService.findAll();
    }
}
