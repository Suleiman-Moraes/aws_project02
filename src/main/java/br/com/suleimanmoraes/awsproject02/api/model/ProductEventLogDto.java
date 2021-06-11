package br.com.suleimanmoraes.awsproject02.api.model;

import br.com.suleimanmoraes.awsproject02.api.enums.EventType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductEventLogDto {
	
	private final String code;
	
    private final EventType eventType;

    private final long productId;

    private final String username;

    private final long timestamp;
    
    private final String messageId;

	public ProductEventLogDto(ProductEventLog productEventLog) {
		this.code = productEventLog.getPk();
		this.eventType = productEventLog.getEventType();
		this.productId = productEventLog.getProductId();
		this.username = productEventLog.getUsername();
		this.timestamp = productEventLog.getTimestamp();
		this.messageId = productEventLog.getMessageId();
	}
}
