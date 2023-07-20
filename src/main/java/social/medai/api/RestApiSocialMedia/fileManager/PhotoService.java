package social.medai.api.RestApiSocialMedia.fileManager;

import social.medai.api.RestApiSocialMedia.exception.NotCreatedException;
import social.medai.api.RestApiSocialMedia.dto.PhotoDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
public class PhotoService {
    public static List<String> savePhoto(List<PhotoDTO> photos){
        Base64.Decoder decoder = Base64.getDecoder();
        List<String> listPaths = new ArrayList<>();

        try {
            for(PhotoDTO photo: photos){
                StringBuilder path = new StringBuilder();
                path.append("C:\\Users\\noche\\OneDrive\\Рабочий стол\\ServerPhoto\\").append(photo.getName());
                FileOutputStream fos = new FileOutputStream(path.toString());
                fos.write(decoder.decode(photo.getFile()));
                fos.close();
                listPaths.add(path.toString());
            }
        } catch (IOException e){
            throw new NotCreatedException("Error of save");
        }
        return listPaths;
    }

    public static String getPhotos(String path){
        Base64.Encoder encoder = Base64.getEncoder();
        List<String> encodedPhotos = new ArrayList<>();

        try {
                StringBuilder stringBytes = new StringBuilder();
                FileInputStream fis = new FileInputStream(path);
                while(fis.available()> 0){
                    stringBytes.append(fis.read());
                }
                fis.close();
                return encoder.encodeToString(stringBytes.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
