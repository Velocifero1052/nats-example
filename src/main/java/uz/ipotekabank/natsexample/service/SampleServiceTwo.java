package uz.ipotekabank.natsexample.service;

import uz.ipotekabank.natsexample.dto.ExampleNatsResponseDto;

import java.util.concurrent.ExecutionException;

public interface SampleServiceTwo {

    ExampleNatsResponseDto exchangeAndGetResult(String message) throws ExecutionException, InterruptedException;

    ExampleNatsResponseDto exchangeAndGetResultParam(String message) throws ExecutionException, InterruptedException;

}
