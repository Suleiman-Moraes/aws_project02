package br.com.suleimanmoraes.awsproject02.api.model;

import br.com.suleimanmoraes.awsproject02.api.enums.EventType;
import lombok.Data;

@Data
public class Envelope {
	private EventType eventType;
	private String data;
}
