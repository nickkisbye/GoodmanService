package dk.goodmanservice.goodmanservice.Controller;

import dk.goodmanservice.goodmanservice.Model.Case;
import dk.goodmanservice.goodmanservice.Model.Image;
import dk.goodmanservice.goodmanservice.Model.Jobs;
import dk.goodmanservice.goodmanservice.Model.User;
import dk.goodmanservice.goodmanservice.Service.BucketService;
import dk.goodmanservice.goodmanservice.Service.CaseService;
import dk.goodmanservice.goodmanservice.Service.IService;
import dk.goodmanservice.goodmanservice.Service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class CaseController {

    @Autowired
    private IService<Case> CS;

    @Autowired
    private IService<User> US;

    @Autowired
    private JobService JS;

    @Autowired
    private BucketService BS;

    /**
     * TILBUD ONLY
     **/
    @GetMapping("/dashboard/tilbud")
    public String offer(Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("case", CS.fetch("offer"));
            model.addAttribute("users", US.fetch("customers"));
            model.addAttribute("edit", false);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/Cases/offer";
    }
    /**
     * OPGAVER ONLY
     **/
    @GetMapping("/dashboard/opgaver")
    public String cases(Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("case", CS.fetch("cases"));
            model.addAttribute("users", US.fetch("customers"));
            model.addAttribute("edit", false);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/Cases/cases";
    }
    /**
     * FÆRDIGE OPGAVER ONLY
     **/
    @GetMapping("/dashboard/faerdigeopgaver")
    public String done(Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("case", CS.fetch("finished"));
            model.addAttribute("users", US.fetch("customers"));
            model.addAttribute("edit", false);
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/Cases/done";
    }

    /**
     * ALL COMBINED
     **/
    @GetMapping("/dashboard/TOF/vis/{id}")
    public String viewTOF(@PathVariable("id") int id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("case", CS.findById(id));
            model.addAttribute("images", BS.fetchImages(id));
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "dashboard/Cases/vis";
    }

    @PostMapping("/dashboard/TOF/fjernBillede/{id}")
    public String deleteImage(@ModelAttribute Image image, @PathVariable("id") int id, RedirectAttributes redirect) {
        try {
            BS.deleteFileFromS3Bucket(image.getFileUrl(), image.getFileId());
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/TOF/redigere/" + id;
    }

    @GetMapping("/dashboard/TOF/redigere/{id}")
    public String editTOF(@PathVariable("id") int id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("case", CS.fetch("offer"));
            model.addAttribute("findById", CS.findById(id));
            model.addAttribute("users", US.fetch("customers"));
            model.addAttribute("edit", true);
            model.addAttribute("images", BS.fetchImages(id));
            model.addAttribute("employees", JS.fetchEmployees(id));
            model.addAttribute("jobs", JS.findByIdJobs(id));
            switch (CS.findById(id).getMode()) {
                case 1:
                    return "dashboard/Cases/offer";
                case 2:
                    return "dashboard/Cases/cases";
                case 3:
                    return "dashboard/Cases/done";
                default:
                    return "dashboard/Cases/no";
            }
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
    }

    @PostMapping("/dashboard/TOF/redigere")
    public String editTOFFORM(@ModelAttribute Case obj, RedirectAttributes ra) {
        try {
            if(CS.edit(obj) == "1") {
                ra.addFlashAttribute("msg", "1");
                ra.addFlashAttribute("edit", false);
                switch (obj.getMode()) {
                    case 1:
                        return "redirect:/dashboard/tilbud";
                    case 2:
                        return "redirect:/dashboard/opgaver";
                    case 3:
                        return "redirect:/dashboard/faerdigeopgaver";
                    default:
                        return "redirect:/dashboard/no";
                }
            } else {
                ra.addFlashAttribute("msg", CS.edit(obj));
                return "redirect:/dashboard/TOF/redigere/"+obj.getId();
            }

        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

    }

    @PostMapping("/dashboard/TOF/upgrade/{id}")
    public String upgradeTOF(@PathVariable("id") int id, @ModelAttribute Case obj, RedirectAttributes ra, Model model) {
        try {
            ra.addFlashAttribute("upgrade", CS.edit(obj));
            ra.addFlashAttribute("edit", false);

            switch (CS.findById(id).getMode()) {
                case 1:
                    return "redirect:/dashboard/tilbud";
                case 2:
                    return "redirect:/dashboard/opgaver";
                case 3:
                    return "redirect:/dashboard/faerdigeopgaver";
                default:
                    return "redirect:/dashboard/error";
            }
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
    }

    @PostMapping("/dashboard/TOF/slet/{id}")
    public String deleteTOF(@PathVariable("id") int id, Model model, RedirectAttributes redirect) {
        try {
            switch (CS.findById(id).getMode()) {
                case 1:
                    CS.delete(id);
                    return "redirect:/dashboard/tilbud";
                case 2:
                    CS.delete(id);
                    return "redirect:/dashboard/opgaver";
                case 3:
                    CS.delete(id);
                    return "redirect:/dashboard/faerdigeopgaver";
                default:
                    return "redirect:/dashboard/error";
            }
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

    }
    @PostMapping("/dashboard/TOF")
    public String createTOF(@ModelAttribute Case obj, Case cases, RedirectAttributes ra, Model model) {
        try {
            ra.addFlashAttribute("msg", CS.create(cases));

            switch (obj.getMode()) {
                case 1:
                    return "redirect:/dashboard/tilbud";
                case 2:
                    return "redirect:/dashboard/opgaver";
                case 3:
                    return "redirect:/dashboard/faerdigeopgaver";
                default:
                    return "redirect:/dashboard/error";
            }
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

    }

    @PostMapping("/dashboard/TOF/NytJob")
    public String createJob(@ModelAttribute Jobs jobs, RedirectAttributes ra, Model model, RedirectAttributes redirect) {
        try {
            ra.addFlashAttribute("error", JS.createJob(jobs));
            return "redirect:/dashboard/TOF/redigere/"+jobs.getCaseId();
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
    }
    @PostMapping("/dashboard/TOF/fjernJob")
    public String removeJob(@ModelAttribute Jobs jobs, RedirectAttributes ra, Model model) {
        try {
            ra.addFlashAttribute("error", JS.deleteJob(jobs.getId()));
            return "redirect:/dashboard/TOF/redigere/"+jobs.getCaseId();
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
    }



}
