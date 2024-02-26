package uz.ipotekabank.natsexample.dto;

import lombok.Data;

@Data
public class ExampleNatsResponseDto {
    String errorMsg;
    String topicName;
    String payload;
}
