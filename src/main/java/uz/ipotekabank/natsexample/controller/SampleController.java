package uz.ipotekabank.natsexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.ipotekabank.natsexample.service.NatsService;
import uz.ipotekabank.natsexample.service.SampleService;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class SampleController {

    private final NatsService natsService;
    private final SampleService sampleService;

    @GetMapping("/first_topic")
    ResponseEntity<?> postSomeMessage(@RequestParam("message")String message) {
        natsService.publishMessageToFirstTopic(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sync_topic")
    ResponseEntity<?> postMessageWithInstantResponse(@RequestParam("message")String message) throws ExecutionException, InterruptedException {
        var resp = natsService.publishMessageToSyncTopic(message);
        System.out.println("##########################");
        System.out.println(resp);
        System.out.println("##########################");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sample_service_method")
    ResponseEntity<?> postMessageToServiceMethod(@RequestParam("message")String message) {
        //var resp = sampleService.sampleListenerMethod(message);
        return ResponseEntity.ok().build();
    }

}
