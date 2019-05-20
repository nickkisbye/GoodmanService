package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    private BucketService bucketService;

    @Autowired
    BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping("uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {

        return this.bucketService.uploadFile(file);
    }

    @DeleteMapping("deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.bucketService.deleteFileFromS3Bucket(fileUrl);
    }
}
