package com.project;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.Random;
import java.util.UUID;

public class InputEventSource implements SourceFunction<InputEvent> {

    private boolean _isRunning;

    @Override
    public void run(SourceContext<InputEvent> sourceContext) throws Exception {
        _isRunning = true;
        while (_isRunning) {
            sourceContext.collect(new InputEvent(
                    UUID.randomUUID().toString(),
                    generateRandomString(200)
            ));
            Thread.sleep(10000);
        }
    }

    @Override
    public void cancel() {
        _isRunning = false;
    }

    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            stringBuilder.append(chars.charAt(index));
        }
        return stringBuilder.toString();
    }
}
