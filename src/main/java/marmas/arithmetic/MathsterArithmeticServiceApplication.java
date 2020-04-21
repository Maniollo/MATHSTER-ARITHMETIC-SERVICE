package marmas.arithmetic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class MathsterArithmeticServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MathsterArithmeticServiceApplication.class, args);
    }

    @PostConstruct
    void postConstruct() {
        log.info("Application initialized.");
    }
}
