package pl.edu.agh.suu.kafkachat.external.dto;

import pl.edu.agh.suu.kafkachat.internal.model.Message;

public record MessageCreationRequest(
        String username,
        String content
) {
    public Message toMessage() {
        return new Message(username, content, System.currentTimeMillis());
    }
}
