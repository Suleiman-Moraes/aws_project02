package br.com.suleimanmoraes.awsproject02.api.service;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.suleimanmoraes.awsproject02.api.model.Envelope;
import br.com.suleimanmoraes.awsproject02.api.model.ProductEvent;
import br.com.suleimanmoraes.awsproject02.api.model.SnsMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductEventConsumer {

    private ObjectMapper objectMapper;

    @Autowired
    public ProductEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage)
            throws JMSException, IOException {

        SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(),
                SnsMessage.class);

        Envelope envelope = objectMapper.readValue(snsMessage.getMessage(),
                Envelope.class);

        ProductEvent productEvent = objectMapper.readValue(
                envelope.getData(), ProductEvent.class);

        log.info("Product event received - Event: {} - ProductId: {} - MessageId: {}",
                envelope.getEventType(),
                productEvent.getId(),
                snsMessage.getMessageId());
    }
}













