package yps.systems.ai.client.models;

public record User(Long idUser, Long idPerson, String username, String password) {

    public User(Long idPerson, String username, String password) {
        this(null, idPerson, username, password);
    }

}
