package dev.aest.ark.controller;

import dev.aest.ark.entity.LocalCredentials;
import dev.aest.ark.entity.User;
import dev.aest.ark.model.RegistrationFormData;
import dev.aest.ark.service.CredentialsService;
import dev.aest.ark.service.ProfilePictureService;
import dev.aest.ark.validation.DuplicateUsernameValidator;
import dev.aest.ark.validation.DuplicateEmailValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AuthController
{
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String REGISTER_ENDPOINT = "/register";

    private final CredentialsService credentialsService;
    private final DuplicateUsernameValidator duplicateUsernameValidator;
    private final DuplicateEmailValidator duplicateEmailValidator;
    private final ProfilePictureService profilePictureService;

    @GetMapping(REGISTER_ENDPOINT)
    public String registerPage(Model model){
        model.addAttribute("form", new RegistrationFormData());
        return "auth/formRegister";
    }

    @PostMapping(REGISTER_ENDPOINT)
    public String registerUser(
            @Valid @ModelAttribute("form") final RegistrationFormData formData,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile image,
            Model model) {
        this.duplicateEmailValidator.validate(formData, bindingResult);
        this.duplicateUsernameValidator.validate(formData, bindingResult);
        if (bindingResult.hasErrors()) return "auth/formRegister";
        LocalCredentials newUser = credentialsService.registerNewUser(formData, image);
        model.addAttribute("email", newUser.getUser().getEmail());
        model.addAttribute("username", newUser.getUsername());
        return "auth/successfulRegister";
    }

    @GetMapping(LOGIN_ENDPOINT)
    public String loginPage(
            @RequestParam(value = "error", required = false) final Boolean loginError,
            Model model){
        model.addAttribute("error", loginError);
        return "auth/formLogin";
    }

    @GetMapping("/success")
    public String authenticationSuccess(){
        return "redirect:/";
    }

    @GetMapping("/oauth2-success")
    public String oauth2AuthSuccess(){
        return "redirect:/";
    }
}
