package yps.systems.ai.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.Person;

@Service
public class PersonService implements IPersonService{

    @Autowired
    private WebClient.Builder client;

    @Value("${env.backend.urls.PersonService}")
    private String personURL;

    @Override
    public Mono<Person> save(Person person) {
        return client
                .build()
                .post()
                .uri(personURL + "/save")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(Person.class);
    }

}
