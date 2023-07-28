package social.medai.api.RestApiSocialMedia.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import social.medai.api.RestApiSocialMedia.exception.*;
import social.medai.api.RestApiSocialMedia.dto.PhotoDTO;
import social.medai.api.RestApiSocialMedia.dto.PostDTO;
import social.medai.api.RestApiSocialMedia.dto.ListPostDTOResponse;
import social.medai.api.RestApiSocialMedia.services.PostService;
import social.medai.api.RestApiSocialMedia.services.UserService;
import social.medai.api.RestApiSocialMedia.util.PhotoValidator;
import social.medai.api.RestApiSocialMedia.util.PostValidator;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "PostsController", description = "Manages posts")
public class PostsController {

    private final PostValidator postValidator;
    private final PostService postService;
    private final PhotoValidator photoValidator;
    private final UserService userService;

    @PostMapping("/create")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Create post"
    )
    public ResponseEntity<HttpStatus> createPost(@RequestBody @Valid PostDTO postDTO,
                                                 BindingResult bindingResult){
        validatePostsSaveOrUpdate(postDTO,bindingResult);
        postService.createPost(postDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Issues post by Id of user")
    public ResponseEntity<ListPostDTOResponse> getPostsByUserId(@PathVariable(value = "id", required = false) int id){
        return ResponseEntity.ok(userService.getPostsByUserId(id));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete post by Id")
    public HttpStatus deletePost(@PathVariable("id") int id){
        postService.deletePost(id);
        return HttpStatus.OK;
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Update post by Id ")
    public HttpStatus updatePost(@PathVariable("id") int id,
                                 @RequestBody @Valid PostDTO postDTO,
                                 BindingResult bindingResult){
        validatePostsSaveOrUpdate(postDTO,bindingResult);
        postService.updatePost(postDTO,id);
        return HttpStatus.OK;
    }

    private void validatePostsSaveOrUpdate(PostDTO postDTO, BindingResult bindingResult){

        postValidator.validate(postDTO,bindingResult);
        if(postDTO.getPhotos() != null){
            for(PhotoDTO photoDTO:postDTO.getPhotos()){
                photoValidator.validate(photoDTO,bindingResult);
            }
        }
        CheckException.checkException(bindingResult);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handOperationFailed(OperationFailed operationFailed){
        ErrorResponse response = new ErrorResponse(
                operationFailed.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handNotCreated(NotCreatedException notCreatedException){
        ErrorResponse response = new ErrorResponse(
                notCreatedException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handNotFound(NotFoundException notFoundException){
        ErrorResponse response = new ErrorResponse(
                notFoundException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
