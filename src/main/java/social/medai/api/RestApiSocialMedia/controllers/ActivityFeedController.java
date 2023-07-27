package social.medai.api.RestApiSocialMedia.controllers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import social.medai.api.RestApiSocialMedia.dto.ListPostDTOResponse;
import social.medai.api.RestApiSocialMedia.services.PostService;

@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class ActivityFeedController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(value = "offset", defaultValue = "0")@Min(0) Integer offset,
                                      @RequestParam(value = "limit", defaultValue = "10")@Min(1) @Max(100) Integer limit,
                                      @RequestParam(value = "sort")String sort){
        return ResponseEntity.ok(postService.getLastPosts(offset,limit,sort));
    }
}
