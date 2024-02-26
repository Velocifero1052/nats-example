package uz.ipotekabank.natsexample.service.implementation;

import io.nats.client.Message;
import org.springframework.stereotype.Service;
import uz.ipotekabank.natsexample.dto.ExampleNatsResponseDto;
import uz.ipotekabank.natsexample.service.NatsService;
import uz.ipotekabank.natsexample.service.SampleServiceOne;

import static uz.ipotekabank.natsexample.cosnt.NatsTopics.METHOD_TOPIC;

@Service
public class SampleServiceOneImplementation implements SampleServiceOne {

    public SampleServiceOneImplementation(NatsService natsService) {
        natsService.getDispatcher().subscribe(METHOD_TOPIC, msg -> {
            var res = sampleListenerMethod(msg);
            natsService.publishMessage(msg.getReplyTo(), res);
        });
    }

    @Override
    public ExampleNatsResponseDto sampleListenerMethod(Message message) {
        var exampleResponse = new ExampleNatsResponseDto();
        exampleResponse.setErrorMsg("");
        exampleResponse.setPayload(new String(message.getData()));
        exampleResponse.setTopicName(message.getSubject());
        return exampleResponse;
    }

}
