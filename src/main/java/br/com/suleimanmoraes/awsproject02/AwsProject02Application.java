package br.com.suleimanmoraes.awsproject02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@EnableAdminServer
@SpringBootApplication
public class AwsProject02Application {

	public static void main(String[] args) {
		SpringApplication.run(AwsProject02Application.class, args);
	}

}
