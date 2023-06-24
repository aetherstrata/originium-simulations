package dev.aest.ark.controller;

import dev.aest.ark.AestArk;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController
{
    @ModelAttribute("project")
    public String addProjectName(){
        return AestArk.PROJECT_NAME;
    }
}
