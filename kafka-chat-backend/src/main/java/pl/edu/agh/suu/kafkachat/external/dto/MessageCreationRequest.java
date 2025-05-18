package pl.edu.agh.suu.kafkachat.external.dto;

import pl.edu.agh.suu.kafkachat.internal.model.Message;

public record MessageCreationRequest(
        String content
) {

    public Message toMessage() {
        return new Message(System.currentTimeMillis(), content);
    }
}
