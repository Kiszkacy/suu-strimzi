package pl.edu.agh.suu.kafkachat.external;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.suu.kafkachat.external.dto.MessageCreationRequest;
import pl.edu.agh.suu.kafkachat.external.dto.MessageDto;
import pl.edu.agh.suu.kafkachat.internal.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> getMessages() {
        return messageService.getMessages().stream()
                .map(MessageDto::fromMessage)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void postMessage(@RequestBody MessageCreationRequest messageCreationRequest) {
        messageService.sendMessage(messageCreationRequest.toMessage());
    }
}
