package social.medai.api.RestApiSocialMedia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.medai.api.RestApiSocialMedia.exception.ErrorResponse;
import social.medai.api.RestApiSocialMedia.exception.NotFoundException;
import social.medai.api.RestApiSocialMedia.exception.OperationFailed;
import social.medai.api.RestApiSocialMedia.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserRelationshipController {
    private final UserService userService;

    @PostMapping("/follow/{id}")
    public HttpStatus requestToFollow(@PathVariable(value = "id", required = false) int id){
        userService.addFollower(id);
        return HttpStatus.OK;
    }

    @PostMapping("/addFriend/{id}")
    public HttpStatus requestToAddFriend(@PathVariable(value = "id", required = false) int id){
        userService.addFriend(id);
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteFriend/{id}")
    public HttpStatus deleteFriend(@PathVariable(value = "id", required = false) int id){
        userService.deleteFriend(id);
        return HttpStatus.OK;
    }

    @PostMapping("/unfollow/{id}")
    public HttpStatus unfollow(@PathVariable(value = "id") int id){
        userService.unfollow(id);
        return HttpStatus.OK;
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handNotFound(NotFoundException notFoundException){
        ErrorResponse response = new ErrorResponse(
                notFoundException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handOperationFailed(OperationFailed operationFailed){
        ErrorResponse response = new ErrorResponse(
                operationFailed.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
