package productservice.api.product_service;

import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PSApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(PSApplicationTests.class);
	@ServiceConnection
	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		log.info("Port: {}", port);
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		postgreSQLContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				    {
				        "name": "Iphone 16",
				        "description": "Xyeta",
				        "price": 1000
				    }
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.log().all()
				.when()
				.post("/api/product");
//				.then()
//				.statusCode(201)
//				.body("id", Matchers.notNullValue())
//				.body("name", Matchers.equalTo("Iphone 16"))
//				.body("description", Matchers.equalTo("Xyeta"))
//				.body("price", Matchers.equalTo(1000));
	}
}
