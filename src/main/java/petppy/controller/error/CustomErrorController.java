package petppy.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private String VIEW_PATH = "errors/";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());

            // 403 에러
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return VIEW_PATH + "403";
            }

            // 404 에러
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return VIEW_PATH + "404";
            }

            // 500 에러
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return VIEW_PATH + "500";
            }


        }
        return "error";
    }

}
