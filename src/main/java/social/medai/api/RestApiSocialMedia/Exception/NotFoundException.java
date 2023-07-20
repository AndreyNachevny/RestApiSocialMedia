package social.medai.api.RestApiSocialMedia.Exception;

public class NotFoundException extends RuntimeException{
    private String message;

    public NotFoundException(String message){
        super(message);
    }
}
