package social.medai.api.RestApiSocialMedia.Exception;

public class OperationFailed extends RuntimeException{
    private String message;

    public OperationFailed(String message){
        super(message);
    }
}
