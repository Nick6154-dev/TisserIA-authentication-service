package yps.systems.ai.client.service;

import reactor.core.publisher.Mono;
import yps.systems.ai.client.models.Person;

public interface IPersonService {

    Mono<Person> save(Person person);

}
