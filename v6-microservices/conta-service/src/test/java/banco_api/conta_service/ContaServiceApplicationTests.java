package banco_api.conta_service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

//Teste contexto
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Tag("integration")
class ContaServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
