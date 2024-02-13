package yps.systems.ai.client.service;

import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.Role;

public interface IRoleService {

    Mono<Role> findById(Long idRole);

    Mono<Role> findByRoleName(String roleName);

}
