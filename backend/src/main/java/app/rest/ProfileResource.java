package app.rest;

import app.domain.Watched;
import app.domain.derivatives.UserInfo;
import app.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping("/friends/{friend}/add")
    public boolean addFriend(@PathVariable String username, @PathVariable String friend) {
        return profileService.addFriend(username, friend);
    }

    @PostMapping("/friends/{friend}/remove")
    public boolean removeFriend(@PathVariable String username, @PathVariable String friend) {
        return profileService.removeFriend(username, friend);
    }

    @PostMapping("/watched/{title}/add")
    public boolean addWatched(@PathVariable String username, @PathVariable String title, @RequestBody Watched body) {
        if (body == null) {
            body = new Watched(null, null, new Date());
        } else {
            if (body.getDate() == null) {
                body.setDate(new Date());
            }
        }
        return profileService.addWatched(username, title, body);
    }

    @PostMapping("/watched/{title}/remove")
    public boolean removeWatched(@PathVariable String username, @PathVariable String title) {
        return profileService.removeWatched(username, title);
    }

   @PostMapping("/watchlist/{title}/add")
   public boolean addWatchlist(@PathVariable String username, @PathVariable String title) {
         return profileService.addWatchlist(username, title);
   }

    @PostMapping("/watchlist/{title}/remove")
    public boolean removeWatchlist(@PathVariable String username, @PathVariable String title) {
        return profileService.removeWatchlist(username, title);
    }
}
