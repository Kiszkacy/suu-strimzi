package pl.edu.agh.suu.kafkachat.external;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.suu.kafkachat.external.dto.MessageCreationRequest;
import pl.edu.agh.suu.kafkachat.external.dto.MessageDto;
import pl.edu.agh.suu.kafkachat.internal.MessageService;
import pl.edu.agh.suu.kafkachat.internal.model.Message;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebSocketMessageController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketMessageController.class);

    private final MessageService messageService;
    private final Tracer tracer;

    public WebSocketMessageController(MessageService messageService, Tracer tracer) {
        this.messageService = messageService;
        this.tracer = tracer;
    }

    @MessageMapping("/messages/send") // Client sends to /app/messages/send
    public void receiveMessage(MessageCreationRequest request) {
        logger.info("Received WebSocket message: {}", request);
        Span span = tracer.spanBuilder("WebSocket: receiveMessage").startSpan();

        try (Scope scope = span.makeCurrent()) {
            Message message = request.toMessage();
            messageService.sendMessage(message);
        } finally {
            span.end();
        }

    }
}

