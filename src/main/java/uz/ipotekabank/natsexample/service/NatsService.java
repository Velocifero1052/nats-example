package uz.ipotekabank.natsexample.service;

import io.nats.client.Dispatcher;
import io.nats.client.Message;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface NatsService {

    Dispatcher getDispatcher();

    void publishMessageToFirstTopic(String message);

    Map<String, String> publishMessageToSyncTopic(String message)  throws ExecutionException,
            InterruptedException;

    void firstTopicListener(Message message);

    Map<String, String> syncTopicListener(Message message);

    void publishMessage(String topicName, Object jsonString);
}
