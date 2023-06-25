package dev.aest.ark.controller;

import dev.aest.ark.OriginiumSimulations;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController
{
    @ModelAttribute("project")
    public String addProjectName(){
        return OriginiumSimulations.PROJECT_NAME;
    }
}
