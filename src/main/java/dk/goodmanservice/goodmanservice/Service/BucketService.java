package dk.goodmanservice.goodmanservice.Service;

import dk.goodmanservice.goodmanservice.Model.Image;
import dk.goodmanservice.goodmanservice.Repository.S3Repository;
import dk.goodmanservice.goodmanservice.Repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

@Service
public class BucketService {

    @Autowired
    private BucketRepository bucketRepository;

    @Autowired
    private S3Repository s3Repository;

    public void deleteFileFromS3Bucket(String fileUrl, int id) throws SQLException {
        s3Repository.deleteFileFromS3Bucket(fileUrl, id);
    }

    public void uploadImage(MultipartFile multipartFile, int id) throws SQLException {
        s3Repository.uploadImage(multipartFile, id);
    }

    public List<Image> fetchImages(int id) throws SQLException {
        ResultSet rs = bucketRepository.fetchImages(id);
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