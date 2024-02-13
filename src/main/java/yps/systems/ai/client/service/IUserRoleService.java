package yps.systems.ai.client.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserRoleService {

    Flux<String> getRolesAssignedToIdPerson(Long idPerson);

    Mono<Boolean> createUserRoleRelation(Long idUser, Long idRole);

}
