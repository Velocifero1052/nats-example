package uz.ipotekabank.natsexample.service.implementation;

import com.google.gson.Gson;
import io.nats.client.Connection;
import io.nats.client.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ipotekabank.natsexample.dto.ExampleNatsRequestDto;
import uz.ipotekabank.natsexample.dto.ExampleNatsResponseDto;
import uz.ipotekabank.natsexample.service.NatsService;
import uz.ipotekabank.natsexample.service.SampleServiceTwo;

import java.util.concurrent.ExecutionException;

import static uz.ipotekabank.natsexample.cosnt.NatsTopics.METHOD_TOPIC;

@Service
@RequiredArgsConstructor
public class SampleServiceTwoImplementation implements SampleServiceTwo {

    private final Connection connection;
    private final Gson gson;
    private final NatsService natsService;

    @Override
    public ExampleNatsResponseDto exchangeAndGetResult(String message) throws ExecutionException, InterruptedException {
        var s = connection.request(METHOD_TOPIC, message.getBytes())
                .thenApply(Message::getData)
                .thenApply(String::new);
        return gson.fromJson(s.get(), ExampleNatsResponseDto.class);
    }

    @Override
    public ExampleNatsResponseDto exchangeAndGetResultParam(String message) throws ExecutionException, InterruptedException {
        var res = natsService.makeRequest(METHOD_TOPIC, message, ExampleNatsResponseDto.class);
        System.out.println("-------------------------");
        System.out.println(res);
        System.out.println("------------------------");
        return res;
    }

    @Override
    public ExampleNatsResponseDto exchangeTypedMessage(ExampleNatsRequestDto dto) throws ExecutionException, InterruptedException {
        var res = natsService.makeRequest(METHOD_TOPIC, dto, ExampleNatsResponseDto.class);
        System.out.println("---------------------------");
        System.out.println(res);
        System.out.println("---------------------------");
        return res;
    }

}
