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

/**
 * Lavet af Joachim
 */

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
    public String offer(Model model, RedirectAttributes redirect, HttpSession session) {
        try {
            model.addAttribute("case", CS.fetch("offer"));
            model.addAttribute("caseById", ((CaseService)CS).fetchById((int)session.getAttribute("id"),"offer"));
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
    public String cases(Model model, RedirectAttributes redirect, HttpSession session) {
        try {
            model.addAttribute("case", CS.fetch("cases"));
            model.addAttribute("caseById", ((CaseService)CS).fetchById((int)session.getAttribute("id"),"cases"));
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
    public String done(Model model, RedirectAttributes redirect, HttpSession session) {
        try {
            model.addAttribute("case", CS.fetch("finished"));
            model.addAttribute("caseById", ((CaseService)CS).fetchById((int)session.getAttribute("id"),"finished"));
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
            redirect.addFlashAttribute("msg", BS.deleteFileFromS3Bucket(image.getFileUrl(), image.getFileId()));
        } catch (SQLException e) {
            redirect.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
        return "redirect:/dashboard/TOF/redigere/" + id;
    }

    @GetMapping("/dashboard/TOF/redigere/{id}")
    public String editTOF(@PathVariable("id") int id, Model model, RedirectAttributes redirect) {
        try {
            model.addAttribute("findById", CS.findById(id));
            model.addAttribute("users", US.fetch("customers"));
            model.addAttribute("edit", true);
            model.addAttribute("images", BS.fetchImages(id));
            model.addAttribute("employees", JS.fetchEmployees(id));
            model.addAttribute("jobs", JS.findByIdJobs(id));
            switch (CS.findById(id).getMode()) {
                case 1:
                    model.addAttribute("case", CS.fetch("offer"));
                    return "dashboard/Cases/offer";
                case 2:
                    model.addAttribute("case", CS.fetch("cases"));
                    return "dashboard/Cases/cases";
                case 3:
                    model.addAttribute("case", CS.fetch("finished"));
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
            String msg = CS.edit(obj);
            ra.addFlashAttribute("msg", msg);

            if(msg.equals("OPGAVEN ER BLEVET REDIGERET")) {
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
                return "redirect:/dashboard/TOF/redigere/"+obj.getId();
            }
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

    }

    @PostMapping("/dashboard/TOF/upgrade/{id}")
    public String upgradeTOF(@PathVariable("id") int id, @ModelAttribute Case obj, RedirectAttributes ra) {
        try {
            ra.addFlashAttribute("upgrade", CS.edit(obj));
            ra.addFlashAttribute("edit", false);
            ra.addFlashAttribute("msg", "OPGAVEN ER BLEVET OPDATERET");
            switch (CS.findById(id).getMode()) {
                case 1:
                    return "redirect:/dashboard/tilbud";
                case 2:
                    return "redirect:/dashboard/opgaver";
                case 3:
                    return "redirect:/dashboard/faerdigeopgaver";
                default:
                    return "redirect:/error";
            }
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
    }

    @PostMapping("/dashboard/TOF/slet/{id}")
    public String deleteTOF(@PathVariable("id") int id, RedirectAttributes ra) {
        try {
            int mode = CS.findById(id).getMode();
            ra.addFlashAttribute("msg", CS.delete(id));
            switch (mode) {
                case 1:
                    return "redirect:/dashboard/tilbud";
                case 2:
                    return "redirect:/dashboard/opgaver";
                case 3:
                    return "redirect:/dashboard/faerdigeopgaver";
                default:
                    return "redirect:/error";
            }
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

    }
    @PostMapping("/dashboard/TOF")
    public String createTOF(@ModelAttribute Case obj, Case cases, RedirectAttributes ra) {
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
                    return "redirect:/error";
            }
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }

    }

    @PostMapping("/dashboard/TOF/NytJob")
    public String createJob(@ModelAttribute Jobs jobs, RedirectAttributes ra) {
        try {
            ra.addFlashAttribute("msg", JS.createJob(jobs));
            return "redirect:/dashboard/TOF/redigere/"+jobs.getCaseId();
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
    }
    @PostMapping("/dashboard/TOF/fjernJob/{id}")
    public String removeJob(@PathVariable("id") int caseId, @ModelAttribute Jobs jobs, RedirectAttributes ra) {
        try {
            ra.addFlashAttribute("msg", JS.deleteJob(jobs.getId()));
            return "redirect:/dashboard/TOF/redigere/"+ caseId;
        } catch (SQLException e) {
            ra.addFlashAttribute("errorCode", e.getErrorCode());
            return "redirect:/error";
        }
    }
}
