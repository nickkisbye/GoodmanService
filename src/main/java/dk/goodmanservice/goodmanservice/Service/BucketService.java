package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Image;
import dk.goodmanservice.goodmanservice.Repository.S3Repository;
import dk.goodmanservice.goodmanservice.Repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

/**
 * Lavet af Nick med hjælp fra guide på internettet.
 * Se bilag i rapporten for at komme til linket.
 */

@Service
public class BucketService {

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private S3Repository s3Repository;

    @Autowired
    private Validation V;

    /**
     BucketService sørger for al forretningslogikken der bliver sendt til AWS og vores database.
     */

    /**
    Når vi sletter et billede fra S3-serveren, laver vi også et kald der sletter billede URL'en fra databasen.
     */

    public String deleteFileFromS3Bucket(String fileUrl, int id) throws SQLException {
        s3Repository.deleteFileFromS3Bucket(fileUrl);
        bucketRepository.deleteImage(id);
        return "BILLEDET ER BLEVET FJERNET";
    }

    /**
     uploadImage metoden til AWS returnerer en URL-string, som vi sætter ind i datbasen, så vi senere kan hente
     den ud og få vist billedet.
     */

    public String uploadImage(MultipartFile multipartFile, int id) throws SQLException, IOException {
        String checkSum = V.validateImage(multipartFile);
        if(checkSum.equals("1")) {
            bucketRepository.insertImage(s3Repository.uploadImage(multipartFile), id);
            return "BILLEDET ER BLEVET TILFØJET";
        }
       return checkSum;
    }

    /**
     fetchImages henter billed URL'en fra databasen, så billedet kan vises
     */

    public List<Image> fetchImages(int id) throws SQLException {
        ResultSet rs =  bucketRepository.fetchImages(id);
        List<Image> imageList = new ArrayList<>();
        while (rs.next()) {
            Image image = new Image();
            image.setFileUrl(rs.getString("image"));
            image.setFileId(rs.getInt("id"));
            imageList.add(image);
        }
        return imageList;
    }
}