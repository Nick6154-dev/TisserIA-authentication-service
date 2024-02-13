package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.JWTRequest;

@Service
public class JWTService implements IJWTService{

    @Autowired
    private WebClient.Builder client;

    @Value("${env.backend.urls.JWTService}")
    private String jwtURL;

    @Override
    public Mono<String> buildJWT(JWTRequest jwtRequest) {
        return client
                .build()
                .post()
                .uri(jwtURL + "/buildJWT")
                .bodyValue(jwtRequest)
                .retrieve()
                .bodyToMono(String.class);
    }
}
