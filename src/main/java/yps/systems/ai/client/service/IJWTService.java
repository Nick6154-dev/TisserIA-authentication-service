package yps.systems.ai.client.service;

import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.JWTRequest;

public interface IJWTService {

    Mono<String> buildJWT(JWTRequest jwtRequest);

}
