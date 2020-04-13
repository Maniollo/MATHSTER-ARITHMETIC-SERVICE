package marmas.arithmetic

import marmas.arithmetic.service.AdditionResultAttemptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MathsterArithmeticServiceApplicationSpecIT extends Specification {
    @Autowired
    AdditionResultAttemptService additionResultAttemptService

    def "should use an additionalResultAttemptService"() {
        expect:
        additionResultAttemptService.verifyAdditionAttempt(1, 2, 3)
    }
}
