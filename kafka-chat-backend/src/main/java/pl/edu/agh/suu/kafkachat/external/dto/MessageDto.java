package pl.edu.agh.suu.kafkachat.external.dto;

import pl.edu.agh.suu.kafkachat.internal.model.Message;

public record MessageDto(
        long timestamp,
        String content
) {

    public static MessageDto fromMessage(Message message) {
        return new MessageDto(message.timestamp(), message.content());
    }
}
