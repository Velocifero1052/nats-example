package uz.ipotekabank.natsexample.service;

import io.nats.client.Message;
import uz.ipotekabank.natsexample.dto.ExampleNatsResponseDto;

public interface SampleService {

    ExampleNatsResponseDto sampleListenerMethod(Message message);

}
