package com.csinfotechbd.exception;
/*
Created by Monirul Islam at 10/1/2019
*/

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest, Model model) {
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("exceptionMessage", "");
                return "404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("exceptionMessage", "You don't have access on this page");
                return "403";
            }
        }
        model.addAttribute("exceptionMessage", "");
        return "404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
