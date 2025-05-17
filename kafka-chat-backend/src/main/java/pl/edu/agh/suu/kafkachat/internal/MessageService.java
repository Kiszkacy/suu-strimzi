package pl.edu.agh.suu.kafkachat.internal;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.edu.agh.suu.kafkachat.internal.model.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MessageService {
    private static final String TOPIC = "messages";

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final List<Message> messageBuffer = new CopyOnWriteArrayList<>();

    public MessageService(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<Message> getMessages() {
        return List.copyOf(messageBuffer);
    }

    public void sendMessage(Message message) {
        kafkaTemplate.send(TOPIC, message);
    }

    @KafkaListener(topics = TOPIC)
    public void listen(ConsumerRecord<String, Message> record) {
        messageBuffer.add(record.value());
    }
}
