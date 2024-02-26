package uz.ipotekabank.natsexample.service.implementation;

import io.nats.client.Message;
import org.springframework.stereotype.Service;
import uz.ipotekabank.natsexample.dto.ExampleNatsResponseDto;
import uz.ipotekabank.natsexample.service.NatsService;
import uz.ipotekabank.natsexample.service.SampleService;

import java.util.Map;

import static uz.ipotekabank.natsexample.cosnt.NatsTopics.METHOD_TOPIC;

@Service
public class SampleServiceImplementation implements SampleService {

    public SampleServiceImplementation(NatsService natsService) {
        natsService.getDispatcher()
                .subscribe(METHOD_TOPIC, msg -> {
                    var res = sampleListenerMethod(msg);
                    natsService.publishMessage(msg.getReplyTo(), res);
                });
    }

    @Override
    public ExampleNatsResponseDto sampleListenerMethod(Message message) {
        var exampleResponse = new ExampleNatsResponseDto();
        exampleResponse.setErrorMsg("");
        exampleResponse.setPayload(Map.of("message", message));
        exampleResponse.setTopicName("");
        return exampleResponse;
    }

}
