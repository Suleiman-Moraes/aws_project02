package br.com.suleimanmoraes.awsproject02.api.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import br.com.suleimanmoraes.awsproject02.api.model.ProductEventKey;
import br.com.suleimanmoraes.awsproject02.api.model.ProductEventLog;

@EnableScan
public interface ProductEventLogRepository extends CrudRepository<ProductEventLog, ProductEventKey> {
	
	List<ProductEventLog> findAllByPk(String pk);

	List<ProductEventLog> findAllByPkAndSkStartsWith(String pk, String sk);
}
