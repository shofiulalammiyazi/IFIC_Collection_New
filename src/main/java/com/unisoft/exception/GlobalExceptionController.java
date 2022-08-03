package com.unisoft.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler(value = NotFoundException.class)
    public String exception(NotFoundException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "404";
    }

    @ExceptionHandler(value = FobiddenException.class)
    public String exception(FobiddenException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "403";
    }

    @ExceptionHandler(value = Exception.class)
    public String exception(Exception e, Model model) {
        log.debug(e.getMessage());
        System.out.println(e.getMessage());
        model.addAttribute("exceptionMessage", e.getMessage());
        return "error";
    }
}
