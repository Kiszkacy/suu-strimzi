package pl.edu.agh.suu.kafkachat.internal;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.edu.agh.suu.kafkachat.external.dto.MessageDto;
import pl.edu.agh.suu.kafkachat.internal.model.Message;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final String TOPIC = "messages";

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final Tracer tracer;


    public MessageService(KafkaTemplate<String, Message> kafkaTemplate, SimpMessagingTemplate messagingTemplate, Tracer tracer) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
        this.tracer = tracer;
    }

    public void sendMessage(Message message) {
        kafkaTemplate.send(TOPIC, message).whenComplete((result, ex) -> {
            if (ex != null) {
                throw new RuntimeException(ex);
            }
        });
    }

    @KafkaListener(topics = TOPIC)
    public void listen(ConsumerRecord<String, Message> record) {
        logger.info("Sending message to listeners: {}", record.value());
        Span span = tracer.spanBuilder("WebSocket: notifyListeners").startSpan();

        try (Scope scope = span.makeCurrent()) {
            messagingTemplate.convertAndSend("/topic/messages", MessageDto.fromMessage(record.value()));
        } finally {
            span.end();
        }

    }
}
