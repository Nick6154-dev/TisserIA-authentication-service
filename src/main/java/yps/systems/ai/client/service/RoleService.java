package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.Role;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private WebClient.Builder client;

    @Value("${env.backend.urls.RoleService}")
    private String roleURL;

    @Override
    public Mono<Role> findById(Long idRole) {
        return client
                .build()
                .get()
                .uri(roleURL + "/findById/" + idRole)
                .retrieve()
                .bodyToMono(Role.class);
    }

    @Override
    public Mono<Role> findByRoleName(String roleName) {
        return client
                .build()
                .get()
                .uri(roleURL + "/findByRoleName/" + roleName)
                .retrieve()
                .bodyToMono(Role.class);
    }

}
