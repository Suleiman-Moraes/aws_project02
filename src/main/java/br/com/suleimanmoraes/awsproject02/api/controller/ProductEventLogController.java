package br.com.suleimanmoraes.awsproject02.api.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.suleimanmoraes.awsproject02.api.model.ProductEventLogDto;
import br.com.suleimanmoraes.awsproject02.api.repository.ProductEventLogRepository;

@RestController
@RequestMapping("/api")
public class ProductEventLogController {

	@Autowired
	ProductEventLogRepository repository;

	@GetMapping("/events")
	public List<ProductEventLogDto> getAllEvents() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).map(ProductEventLogDto::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/events/{code}")
	public List<ProductEventLogDto> findByCode(@PathVariable String code) {
		return StreamSupport.stream(repository.findAllByPk(code).spliterator(), false).map(ProductEventLogDto::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/events/{code}/{event}")
	public List<ProductEventLogDto> findByCodeAndEventType(@PathVariable String code, @PathVariable String event) {
		return StreamSupport.stream(repository.findAllByPkAndSkStartsWith(code, event).spliterator(), false)
				.map(ProductEventLogDto::new).collect(Collectors.toList());
	}
}
