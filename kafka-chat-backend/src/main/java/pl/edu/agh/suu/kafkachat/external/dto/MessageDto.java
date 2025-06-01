package pl.edu.agh.suu.kafkachat.external.dto;

import pl.edu.agh.suu.kafkachat.internal.model.Message;

public record MessageDto(
        String chatName,
        String username,
        String content,
        long timestamp
        ) {

    public static MessageDto fromMessage(Message message) {
        return new MessageDto(message.chatName(), message.username(), message.content(), message.timestamp());
    }
}
