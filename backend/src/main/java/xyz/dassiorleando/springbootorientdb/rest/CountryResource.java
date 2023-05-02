package xyz.dassiorleando.springbootorientdb.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import xyz.dassiorleando.springbootorientdb.domain.Country;
import xyz.dassiorleando.springbootorientdb.service.CountryService;

import java.util.List;

@RestController
public class CountryResource {
    private final Logger log = LoggerFactory.getLogger(CountryResource.class);

    private final CountryService countryService;

    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/country")
    public List<Country> list() {
        log.debug("We just get the list of countries one more time");
        return countryService.findAll();
    }

}
