package dk.goodmanservice.goodmanservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, @ModelAttribute("errorCode") String errorCode, Model model) {
        if (request.getAttribute("javax.servlet.error.status_code") != null && (Integer) request.getAttribute("javax.servlet.error.status_code") == 404) {
            return "ErrorHandling/error404";
        } else if (errorCode != null) {
            model.addAttribute("errorCode", errorCode);
            return "ErrorHandling/errorSql";
        } else {
            return "error";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
