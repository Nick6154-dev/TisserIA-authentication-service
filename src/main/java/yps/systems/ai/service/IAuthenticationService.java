package yps.systems.ai.service;

import reactor.core.publisher.Mono;
import yps.systems.ai.models.SignIn;
import yps.systems.ai.models.SignUp;

public interface IAuthenticationService {

    Mono<String> signIn(SignIn signIn);

    Mono<Boolean> signUp(SignUp signUp);

}
