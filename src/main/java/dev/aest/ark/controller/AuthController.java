package dev.aest.ark.controller;

import dev.aest.ark.entity.LocalCredentials;
import dev.aest.ark.entity.User;
import dev.aest.ark.service.CredentialsService;
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

@Controller
@RequiredArgsConstructor
public class AuthController
{
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String REGISTER_ENDPOINT = "/register";

    private final CredentialsService credentialsService;
    private final DuplicateUsernameValidator duplicateUsernameValidator;
    private final DuplicateEmailValidator duplicateEmailValidator;

    @GetMapping(REGISTER_ENDPOINT)
    public String registerPage(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new LocalCredentials());
        return "auth/formRegister";
    }

    @PostMapping(REGISTER_ENDPOINT)
    public String registerUser(
            @Valid @ModelAttribute("user") final User user,
            BindingResult userBinding,
            @Valid @ModelAttribute("credentials") final LocalCredentials credentials,
            BindingResult credentialsBinding,
            Model model) {
        this.duplicateEmailValidator.validate(user, userBinding);
        this.duplicateUsernameValidator.validate(credentials, credentialsBinding);
        if(!userBinding.hasErrors() && !credentialsBinding.hasErrors()) {
            user.setNickname(credentials.getUsername());
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("email", user.getEmail());
            model.addAttribute("username", credentials.getUsername());
            return "auth/successfulRegister";
        }
        return "auth/formRegister";
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
