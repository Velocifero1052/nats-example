package uz.ipotekabank.natsexample;

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SpringBootApplication
public class NatsExampleApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(NatsExampleApplication.class, args);

      /*  var conn = (Connection)context.getBean("connection");

        var dispatcher = conn.createDispatcher();

        dispatcher.subscribe("my_very_first_topic", msg -> {
            System.out.println("received from topic: " + msg);
        });
*/

    }
    /*
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            var connection = Nats.connect();
            var dispatcher = connection.createDispatcher(
                message -> {
                    System.out.printf("Received Message[%s] from [%s]: %n\n",
                            new String(message.getData(), StandardCharsets.UTF_8),
                            message.getSubject()
                            );
                }
            );
            dispatcher.subscribe("uz.ipotekabank.*");
        };
    }
    */

}
