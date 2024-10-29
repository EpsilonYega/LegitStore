package inventoryservice.api.inventory_service;

import org.springframework.boot.SpringApplication;

public class TestInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ISApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
