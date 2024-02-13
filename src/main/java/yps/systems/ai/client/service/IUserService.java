package yps.systems.ai.client.service;

import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.User;

public interface IUserService {

    Mono<User> save(User user);

    Mono<User> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

}
