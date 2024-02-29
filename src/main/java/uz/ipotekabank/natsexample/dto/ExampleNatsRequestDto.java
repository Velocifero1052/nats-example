package uz.ipotekabank.natsexample.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ExampleNatsRequestDto {
    private String message;
    private Map<String, Object> somePayload;
}
