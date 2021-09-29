package dev.trangelier.oci.ocistreaming.service;

import dev.trangelier.oci.ocistreaming.db.DataMessageDao;
import dev.trangelier.oci.ocistreaming.model.DataMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SampleProducerService {
    private static final Logger logger = LoggerFactory.getLogger(SampleProducerService.class);
    @Autowired
    private DataMessageDao dataMessageDao;
    @Autowired
    private String springBootTopic;

    @Autowired
    private KafkaTemplate<Object, DataMessage> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        // mock building a database record from the request
        DataMessage dataMessage = new DataMessage();
        dataMessage.setId(UUID.randomUUID().toString());
        dataMessage.setRequestId(UUID.randomUUID().toString());
        dataMessage.setMessage(message);
        dataMessageDao.insert(dataMessage);
        // send the same message twice to illustrate that consumers are idempotent
        this.kafkaTemplate.send(springBootTopic, dataMessage.getId(), dataMessage);
        this.kafkaTemplate.send(springBootTopic, dataMessage.getId(), dataMessage);
    }
}
