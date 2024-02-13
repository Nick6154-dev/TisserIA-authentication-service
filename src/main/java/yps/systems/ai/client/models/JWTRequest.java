package yps.systems.ai.client.models;

import java.util.List;

public record JWTRequest(String issuer, String subject, Long idPerson, List<String> roleNames, long expirationTimeMillis) {
}
