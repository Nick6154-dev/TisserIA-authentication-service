package yps.systems.ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.JWTRequest;
import yps.systems.ai.client.models.Person;
import yps.systems.ai.client.models.Role;
import yps.systems.ai.client.models.User;
import yps.systems.ai.client.service.*;
import yps.systems.ai.models.Mail;
import yps.systems.ai.models.SignIn;
import yps.systems.ai.models.SignUp;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private IPersonService personService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IJWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ReactivePulsarTemplate<Mail> pulsarTemplate;

    @Value("${env.backend.email.newUserTisserIdTemplate}")
    private Integer idTemplate;

    @Value("${env.backend.pulsar.topic}")
    private String topic;

    @Override
    public Mono<String> signIn(SignIn signIn) {
        return userService.findByUsername(signIn.username())
                .flatMap(userFounded -> {
                    if (passwordEncoder.matches(signIn.password(), userFounded.password())) {
                        return userRoleService.getRolesAssignedToIdPerson(userFounded.idPerson())
                                .collectList()
                                .flatMap(roleNames -> jwtService
                                        .buildJWT(new JWTRequest(
                                                "https://yps.systems.ec",
                                                "Powered by YPS Systems",
                                                userFounded.idPerson(),
                                                roleNames,
                                                100000000L
                                        )))
                                .onErrorResume(error -> {
                                    System.out.println(error.getMessage());
                                    return Mono.just("None");
                                });
                    } else {
                        return Mono.just("None");
                    }
                })
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just("None");
                });
    }

    @Override
    public Mono<Boolean> signUp(SignUp signUp) {
        return personService.save(new Person(signUp.name(), signUp.lastname(), signUp.email(), signUp.phone()))
                .flatMap(personSaved -> {
                    Mono<User> userMono = userService.save(new User(personSaved.idPerson(), personSaved.email(),
                            passwordEncoder.encode(signUp.password())));
                    Mono<Role> roleMono = roleService.findByRoleName("USER");
                    return Mono.zip(userMono, roleMono)
                            .flatMap(objects -> {
                                User user = objects.getT1();
                                Role role = objects.getT2();
                                return userRoleService.createUserRoleRelation(user.idUser(), role.idRole())
                                        .then(pulsarTemplate.send(topic, new Mail(
                                                        personSaved.name() + " " + personSaved.lastname(),
                                                        personSaved.email(),
                                                        signUp.password(),
                                                        personSaved.email(),
                                                        idTemplate
                                                ))
                                        )
                                        .thenReturn(true);
                            })
                            .onErrorResume(error -> {
                                System.out.println(error.getMessage());
                                return Mono.just(false);
                            });
                })
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(false);
                });
    }

}
