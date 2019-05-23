package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
@RequestMapping("/storage/")
public class BucketController {

    private BucketService bucketService;

    @Autowired
    BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping("uploadImage")
    public String uploadImage(@RequestPart(value = "file") MultipartFile file, @ModelAttribute Case cases, RedirectAttributes redirect) {
        try {
            redirect.addFlashAttribute("msg", this.bucketService.uploadImage(file, cases.getId()));
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

        return "redirect:/dashboard/TOF/redigere/" + cases.getId();
    }

}
