package dev.trangelier.oci.ocistreaming.controller;

import dev.trangelier.oci.ocistreaming.db.ConsumerInboxDao;
import dev.trangelier.oci.ocistreaming.db.DataMessageDao;
import dev.trangelier.oci.ocistreaming.model.ConsumerInbox;
import dev.trangelier.oci.ocistreaming.model.DataMessage;
import dev.trangelier.oci.ocistreaming.service.SampleProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/kafka")
public class SampleProducerController {

    private final SampleProducerService producer;
    private final DataMessageDao dataMessageDao;
    private final ConsumerInboxDao consumerInboxDao;

    @Autowired
    SampleProducerController(SampleProducerService producer, DataMessageDao dataMessageDao, ConsumerInboxDao consumerInboxDao) {
        this.producer = producer;
        this.dataMessageDao = dataMessageDao;
        this.consumerInboxDao = consumerInboxDao;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.producer.sendMessage(message);
    }

    @GetMapping("/")
    public List<DataMessage> findAll(){
        return dataMessageDao.findAll();
    }

    @GetMapping("/consumed")
    public List<ConsumerInbox> findAllConsumed(){
        return consumerInboxDao.findAll();
    }
}
