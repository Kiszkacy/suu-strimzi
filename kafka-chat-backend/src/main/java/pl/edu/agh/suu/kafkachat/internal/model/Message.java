package pl.edu.agh.suu.kafkachat.internal.model;

public record Message(String username, String content, long timestamp) {
}
