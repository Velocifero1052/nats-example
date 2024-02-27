package uz.ipotekabank.natsexample.service.implementation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import lombok.Getter;
import org.springframework.stereotype.Service;
import uz.ipotekabank.natsexample.dto.ExampleNatsResponseDto;
import uz.ipotekabank.natsexample.service.NatsService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static uz.ipotekabank.natsexample.cosnt.NatsTopics.FIRST_TOPIC;
import static uz.ipotekabank.natsexample.cosnt.NatsTopics.METHOD_TOPIC;
import static uz.ipotekabank.natsexample.cosnt.NatsTopics.SYNC_TOPIC;

@Service
public class NatsServiceImplementation implements NatsService {
    private final Gson gson;
    @Getter
    private final Connection natsConnection;
    @Getter
    private final Dispatcher dispatcher;

    public NatsServiceImplementation(Connection natsConnection) {
        gson = new Gson();
        this.natsConnection = natsConnection;
        //creating and initializing dispatcher with subscriptions
        dispatcher = natsConnection.createDispatcher();
        dispatcher.subscribe(FIRST_TOPIC, this::firstTopicListener);
        dispatcher.subscribe(SYNC_TOPIC, msg -> {
            var res = syncTopicListener(msg);
            this.natsConnection.publish(msg.getReplyTo(), gson.toJson(res).getBytes());
        });
    }

    @Override
    public void publishMessageToFirstTopic(String message) {
        var map = new HashMap<String, String>();
        map.put("message", message);
        natsConnection.publish(FIRST_TOPIC, gson.toJson(map).getBytes());
    }

    @Override
    public Map<String, String> publishMessageToSyncTopic(String message) throws ExecutionException, InterruptedException {
        var map = new HashMap<String, String>();
        map.put("message", message);
        //sending message
        var s = natsConnection.request(SYNC_TOPIC, gson.toJson(map).getBytes())
                .thenApply(Message::getData)
                .thenApply(String::new);
        return gson.fromJson(s.get(), new TypeToken<Map<String, String>>() {
        }.getType());
    }

    @Override
    public ExampleNatsResponseDto publicMessageToMethodTopic(String message) throws ExecutionException, InterruptedException {
        var map = new HashMap<String, String>();
        map.put("message", message);
        var s = natsConnection.request(METHOD_TOPIC, gson.toJson(map).getBytes())
                .thenApply(Message::getData)
                .thenApply(String::new);
        return gson.fromJson(s.get(), ExampleNatsResponseDto.class);
    }

    @Override
    public void firstTopicListener(Message message) {
        var map = gson.fromJson(new String(message.getData(), StandardCharsets.UTF_8), Map.class);
        System.out.println("Topic listener: #######################");
        System.out.println(map);
        System.out.println("########################################");
    }

    @Override
    public Map<String, String> syncTopicListener(Message message) {
        return Map.of("topic_name", SYNC_TOPIC,
                "received_value", new String(message.getData(), StandardCharsets.UTF_8));
    }

    @Override
    public void publishMessage(String topicName, Object object) {
        natsConnection.publish(topicName, gson.toJson(object).getBytes());
    }

    @Override
    public <T> T makeRequest(String topic, String jsonString, Class<T> classOfT) throws ExecutionException, InterruptedException {
        var s = this.natsConnection.request(topic, gson.toJson(jsonString).getBytes())
                .thenApply(Message::getData)
                .thenApply(String::new);
        return gson.fromJson(s.get(), classOfT);
    }

}
