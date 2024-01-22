package uz.ipotekabank.natsexample;

import com.google.gson.Gson;
import io.nats.client.Connection;
import io.nats.client.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class SomeController {

    private final Connection natsConnection;
    private final Gson gson;

    @GetMapping("/some_example")
    ResponseEntity<?> postSomeMessage(@RequestParam("message")String message) throws InterruptedException {
        var map = new HashMap<String, String>();
        map.put("some_key", "some_value");
        natsConnection.publish("my_very_first_topic", gson.toJson(map).getBytes());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/some_example_with_response")
    ResponseEntity<?> postMessageWithInstantResponse() throws ExecutionException, InterruptedException {

        var map = new HashMap<String, String>();
        map.put("post values to quee", "some values");

        var s = natsConnection.request("my_sync_topic", gson.toJson(map).getBytes())
                .thenApply(Message::getData)
                .thenApply(String::new);

        var res = gson.fromJson(s.get(), Map.class);

        System.out.println("Result:" + res);

        return ResponseEntity.ok().build();
    }



}
