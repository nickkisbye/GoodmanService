package dk.goodmanservice.goodmanservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, RedirectAttributes redirect) {
        if ((Integer) request.getAttribute("javax.servlet.error.status_code") == 404) {
            return "error404";
        } else if (redirect.containsAttribute("errorCode")) {
            return "errorSql";
        } else {
            return "error";
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
