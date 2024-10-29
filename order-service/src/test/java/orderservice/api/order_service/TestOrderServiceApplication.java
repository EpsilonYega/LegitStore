package orderservice.api.order_service;

import org.springframework.boot.SpringApplication;

public class TestOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(OSApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
