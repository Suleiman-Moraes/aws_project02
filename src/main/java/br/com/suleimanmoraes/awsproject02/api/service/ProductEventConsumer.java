package br.com.suleimanmoraes.awsproject02.api.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.suleimanmoraes.awsproject02.api.model.Envelope;
import br.com.suleimanmoraes.awsproject02.api.model.ProductEvent;
import br.com.suleimanmoraes.awsproject02.api.model.ProductEventLog;
import br.com.suleimanmoraes.awsproject02.api.model.SnsMessage;
import br.com.suleimanmoraes.awsproject02.api.repository.ProductEventLogRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductEventConsumer {

	private ObjectMapper objectMapper;

	private ProductEventLogRepository productEventLogRepository;

	@Autowired
	public ProductEventConsumer(ObjectMapper objectMapper, ProductEventLogRepository productEventLogRepository) {
		this.objectMapper = objectMapper;
		this.productEventLogRepository = productEventLogRepository;
	}

	@JmsListener(destination = "${aws.sqs.queue.product.events.name}")
	public void receiveProductEvent(TextMessage textMessage) throws JMSException, IOException {

		SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

		Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);

		ProductEvent productEvent = objectMapper.readValue(envelope.getData(), ProductEvent.class);

		log.info("Product event received - Event: {} - ProductId: {} - MessageId: {}", envelope.getEventType(),
				productEvent.getId(), snsMessage.getMessageId());

		ProductEventLog productEventLog = buildProductEventLog(envelope, productEvent, snsMessage);
		log.info("Product event pre salvo - {}", productEventLog.toString());
		productEventLogRepository.save(productEventLog);
		log.info("Product event pos salvo - {}", productEventLog.toString());
	}

	private ProductEventLog buildProductEventLog(Envelope envelope, ProductEvent productEvent, SnsMessage snsMessage) {
		long timestamp = Instant.now().toEpochMilli();

        ProductEventLog productEventLog = new ProductEventLog();
        productEventLog.setPk(productEvent.getCode());
        productEventLog.setSk(envelope.getEventType() + "_" + timestamp);
        productEventLog.setEventType(envelope.getEventType());
        productEventLog.setProductId(productEvent.getId());
        productEventLog.setUsername(productEvent.getUsername());
        productEventLog.setTimestamp(timestamp);
        productEventLog.setTtl(Instant.now().plus(
                Duration.ofMinutes(10)).getEpochSecond());
        productEventLog.setMessageId(snsMessage.getMessageId());
		return productEventLog;
	}
}
