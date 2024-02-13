package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.User;

@Service
public class UserService implements IUserService{

    @Autowired
    private WebClient.Builder client;

    @Value("${env.backend.urls.UserService}")
    private String userURL;

    @Override
    public Mono<User> save(User user) {
        return client
                .build()
                .post()
                .uri(userURL + "/save")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return client
                .build()
                .get()
                .uri(userURL + "/findByUsername/" + username)
                .retrieve()
                .bodyToMono(User.class);
    }

    @Override
    public Mono<Boolean> existsByUsername(String username) {
        return client
                .build()
                .get()
                .uri(userURL + "/existsByUsername/" + username)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

}
