package uz.ipotekabank.natsexample.service;

import io.nats.client.Message;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface NatsService {

    void publishMessageToFirstTopic(String message);

    Map<String, String> publishMessageToSyncTopic(String message)  throws ExecutionException,
            InterruptedException;

    void firstTopicListener(Message message);

    Map<String, String> syncTopicListener(Message message);

}
