package com.project;

import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.formats.json.*;

public class Main {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        var kafkaSink = KafkaSink.<InputEvent>builder()
                .setBootstrapServers("localhost:9092")
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic("project.input-topic")
                        .setValueSerializationSchema(new JsonSerializationSchema<InputEvent>())
                        .build())
                .setDeliverGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();

        env.addSource(new InputEventSource())
                .map(event -> {
                    System.out.println("Dispatching event" + event.getId());
                    return event;
                })
                .sinkTo(kafkaSink);

        env.execute("String supplier flink job");
    }
}