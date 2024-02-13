package yps.systems.ai.client.models;

public record Person(Long idPerson, String name, String lastname, String email, String phone) {
    public Person(String name, String lastname, String email, String phone) {
        this(null, name, lastname, email, phone);
    }
}
