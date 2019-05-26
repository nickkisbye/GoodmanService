package dk.goodmanservice.goodmanservice.Repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Date;

@Repository
public class S3Repository {

    private AmazonS3 s3client;

    private String endpointUrl = "http://s3.eu-central-1.amazonaws.com";

    private String bucketName = "goodmanservice";

    private String accessKey = "";

    private String secretKey = "";

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }

    /**
     Denne metode tager en MultipartFile ind fra vores form og konverterer den om til et File objekt og returnerer det.
     */

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    /**
     Denne metode genererer et filnavn baseret på den nuværende tid. På den måde vil vores filnavn altid være unikt.
     */

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    /**
     Denne metode pusher vores filobjekt af sted til AWS.
     */

    private void uploadImageTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    /**
     Denne metode instantierer file objektet med convertMultiPartToFile metoden.
     Derefter uploader den billedet til AWS og returnerer den String som vi skal bruge
     til at putte ind i databasen.
     */

    public String uploadImage(MultipartFile multipartFile) throws IOException {

        String fileUrl;
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
        uploadImageTos3bucket(fileName, file);
        file.delete();
        return fileUrl;
    }

    public void deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
    }
}