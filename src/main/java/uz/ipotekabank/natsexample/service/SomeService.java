package uz.ipotekabank.natsexample.service;

import com.google.gson.Gson;
import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class SomeService {

    private Connection connection;
    private final Gson gson;

    public SomeService(Connection connection) {
        gson = new Gson();
        Dispatcher dispatcher = connection.createDispatcher();

        dispatcher.subscribe("my_very_first_topic", this::someMethod);

        dispatcher.subscribe("my_sync_topic", msg -> {
            var res = someMethodWithResponse(msg);
            connection.publish(msg.getReplyTo(), gson.toJson(res).getBytes());
        });

    }

    private void someMethod(Message message) {
        System.out.println("In service call########################");
        var map = gson.fromJson(new String(message.getData(), StandardCharsets.UTF_8), Map.class);
        System.out.println(map);
        System.out.println("#######################################");
    }

    private Map<String, String> someMethodWithResponse(Message message) {
        return Map.of("some_key", "with_some_value", "received_value", new String(message.getData(), StandardCharsets.UTF_8));
    }

}
