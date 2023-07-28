package social.medai.api.RestApiSocialMedia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "UserRelationshipController", description = "User interaction")
public class UserRelationshipController {
    private final UserService userService;

    @PostMapping("/follow/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "You subscribe to someone")
    public HttpStatus requestToFollow(@PathVariable(value = "id", required = false) int id){
        userService.addFollower(id);
        return HttpStatus.OK;
    }

    @PostMapping("/addFriend/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "add a friend",
            description = "add a friend who follows you"
    )
    public HttpStatus requestToAddFriend(@PathVariable(value = "id", required = false) int id){
        userService.addFriend(id);
        return HttpStatus.OK;
    }

    @DeleteMapping("/deleteFriend/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Delete a friend"
    )
    public HttpStatus deleteFriend(@PathVariable(value = "id", required = false) int id){
        userService.deleteFriend(id);
        return HttpStatus.OK;
    }

    @PostMapping("/unfollow/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "unfollow",
            description = "unfollow if you are not friends "
    )
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
