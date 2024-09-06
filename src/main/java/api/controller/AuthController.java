package api.controller;

import api.dto.*;
import config.security.TokenService;
import domain.entity.User;
import domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserOutput> register(@RequestBody @Valid UserInput userInput) {
        UserOutput user = userService.addUser(userInput);
        URI uri = URI.create("/user/" + user.id());
        return ResponseEntity
                .ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenOutput> login(@RequestBody @Valid Login login){
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new TokenOutput((token)));
    }

    @PostMapping("/changepassword")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordInput input){
        userService.changePassword(input);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<Void> forgotPasswordRequest(@RequestBody @Valid String email){
        userService.forgotPasswordRequest(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changepasswordwithcode")
    public ResponseEntity<Void> changePasswordWithCode(@RequestBody @Valid ChangePasswordWithCode input){
        userService.changePasswordWithCode(input);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changeemail")
    public ResponseEntity<Void> changeEmail(@RequestBody @Valid ChangeEmailInput input){
        userService.changeEmail(input);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/activate/{code}")
    public ResponseEntity<Void> activateAccount(@PathVariable String code){
        userService.enableUser(code);
        return ResponseEntity.noContent().build();
    }

}
