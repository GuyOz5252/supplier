package com.project;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.UUID;

public class InfiniteStringSource implements SourceFunction<String> {

    private boolean _isRunning;

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        _isRunning = true;
        while (_isRunning) {
            var uuid = UUID.randomUUID().toString();
            sourceContext.collect(uuid);
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        _isRunning = false;
    }
}
