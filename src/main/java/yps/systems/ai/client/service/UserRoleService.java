package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.Role;

@Service
public class UserRoleService implements IUserRoleService{

    @Autowired
    private WebClient.Builder client;

    @Autowired
    private IRoleService roleService;

    @Value("${env.backend.urls.UserRoleService}")
    private String userRoleURL;

    @Override
    public Flux<String> getRolesAssignedToIdPerson(Long idPerson) {
        return client
                .build()
                .get()
                .uri(userRoleURL + "/getRolesAssignedToIdPerson/" + idPerson)
                .retrieve()
                .bodyToFlux(Long.class)
                .flatMap(idRole -> roleService
                        .findById(idRole)
                        .map(Role::roleName));
    }

    @Override
    public Mono<Boolean> createUserRoleRelation(Long idUser, Long idRole) {
        return client
                .build()
                .post()
                .uri(userRoleURL + "/createUserRoleRelation/" + idUser + "," + idRole)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
