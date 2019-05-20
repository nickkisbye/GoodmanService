package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@Controller
@RequestMapping("/storage/")
public class BucketController {

    private BucketService bucketService;

    @Autowired
    BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping("uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file, @ModelAttribute Case cases) {
        try {
            this.bucketService.uploadFile(file, cases.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "redirect:/dashboard/TOF/redigere/" + cases.getId();
    }

}
