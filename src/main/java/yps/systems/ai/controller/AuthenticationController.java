package yps.systems.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import yps.systems.ai.models.SignIn;
import yps.systems.ai.models.SignUp;
import yps.systems.ai.service.IAuthenticationService;

@RestController
@RequestMapping("/authenticationService")
public class AuthenticationController {

    @Autowired
    private IAuthenticationService service;

    @PostMapping("/signIn")
    public Mono<ResponseEntity<String>> signIn(@RequestBody SignIn signIn){
        return service.signIn(signIn)
                .map(response -> {
                    if (!response.equals("None")) {
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
                    }
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
                })
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error.getMessage()));
                });
    }

    @PostMapping("/signUp")
    public Mono<ResponseEntity<String>> signUp(@RequestBody SignUp signUp) {
        return service.signUp(signUp)
                .map(success -> {
                    if (success) {
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Saved.");
                    }
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Not saved.");
                })
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error.getMessage()));
                });
    }

}
