package uz.ipotekabank.natsexample.component;

import io.nats.client.Connection;

import io.nats.client.Nats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class NatsComponent {

    @Bean("connection")
    public Connection getConnection() throws IOException, InterruptedException {
        return Nats.connect("nats://localhost:4222");
    }

}
