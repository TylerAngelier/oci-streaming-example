package dev.trangelier.oci.ocistreaming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.trangelier.oci.ocistreaming.db.ConsumerInboxDao;
import dev.trangelier.oci.ocistreaming.model.ConsumerInbox;
import dev.trangelier.oci.ocistreaming.model.DataMessage;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class SampleConsumerService {
    private final Logger logger = LoggerFactory.getLogger(SampleConsumerService.class);
    @Autowired
    private ConsumerInboxDao inboxDao;
    @Autowired
    private ObjectMapper mapper;

    private static final String consumerId = "consumer01";
    private static final String broadcastConsumerId = "dataMessageBroadcaster";

    @KafkaListener(id = consumerId, topics = "#{@springBootTopic}")
    @Transactional
    public void consume(String message, Acknowledgment ack) {
        try {
            // parse message
            // TODO: don't just use JSON - use avro, protobuf, or json schema
            DataMessage dataMessage = mapper.readValue(message, DataMessage.class);

            // put message in inbox
            writeToInbox(consumerId, dataMessage.getRequestId(), message, ack);

            // do some business logic on the message... here we're just logging
            logger.info(String.format("#### -> Consumed message -> %s", dataMessage));
            ack.acknowledge();
        } catch(JsonProcessingException ex){
            logger.error("Error transforming message to DataMessage", ex);
        }
    }

    @KafkaListener(id = broadcastConsumerId, topics = "#{@springBootTopic}")
    public void consumeAndBroadcast(String message, Acknowledgment ack) {
        try {
            // parse message
            // TODO: don't just use JSON - use avro, protobuf, or json schema
            DataMessage dataMessage = mapper.readValue(message, DataMessage.class);

            // put message in inbox
            writeToInbox(broadcastConsumerId, dataMessage.getRequestId(), message, ack);

            // do some business logic on the message... here we're just logging
            logger.info(String.format("#### -> Broadcasting message -> %s", message));
            ack.acknowledge();
        } catch(JsonProcessingException ex){
            logger.error("Error transforming message to DataMessage", ex);
        }
    }

    private void writeToInbox(String consumerId, String requestId, String rawMessage, Acknowledgment ack){
        try {
            ConsumerInbox inbox = new ConsumerInbox();
            inbox.setConsumerId(consumerId);
            inbox.setRequestId(requestId);
            inbox.setPayload(rawMessage);
            inboxDao.insert(inbox);
            logger.info(String.format("#### -> Wrote to inbox -> %s", inbox));
        }catch (UnableToExecuteStatementException ex){
            if (ex.getCause() instanceof SQLIntegrityConstraintViolationException){
                SQLIntegrityConstraintViolationException sqlEx = (SQLIntegrityConstraintViolationException) ex.getCause();
                // ORA-00001: unique constraint violated
                if (sqlEx.getErrorCode() == 1){
                    // we ack the message because it has already been processed
                    logger.info("Skipped processing duplicate message with requestId {}", requestId);
                    ack.acknowledge();
                }
            }else {
                logger.error("Unhandled Error occurred", ex);
                throw ex;
            }
        }

    }
}
