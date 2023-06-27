package dev.aest.ark.controller;

import dev.aest.ark.model.LocalCredentials;
import dev.aest.ark.model.User;
import dev.aest.ark.service.CredentialsService;
import dev.aest.ark.service.UserService;
import dev.aest.ark.validation.CredentialsValidator;
import dev.aest.ark.validation.UserValidator;
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
    private final UserService userService;
    private final UserValidator userValidator;

    private final CredentialsService credentialsService;
    private final CredentialsValidator credentialsValidator;

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new LocalCredentials());
        return "auth/formRegister";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") final User user,
            BindingResult userBinding,
            @Valid @ModelAttribute("credentials") final LocalCredentials credentials,
            BindingResult credentialsBinding,
            Model model) {
        this.userValidator.validate(user, userBinding);
        this.credentialsValidator.validate(credentials, credentialsBinding);
        if(!userBinding.hasErrors() && !credentialsBinding.hasErrors()) {
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("email", user.getEmail());
            model.addAttribute("username", credentials.getUsername());
            return "auth/successfulRegister";
        }
        return "auth/formRegister";
    }

    @GetMapping("/login")
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
