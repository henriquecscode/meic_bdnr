package app.rest;

import app.domain.derivatives.UserInfo;
import app.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile/{username}")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);

    private final ProfileService profileService;


    public ProfileResource(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping("")
    public UserInfo findByUser(@PathVariable String username) {
        UserInfo userInfo = profileService.getInfoByUsername(username);
        return userInfo;
    }
}
