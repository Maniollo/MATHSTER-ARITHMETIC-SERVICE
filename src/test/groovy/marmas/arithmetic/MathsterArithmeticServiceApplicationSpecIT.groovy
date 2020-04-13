package marmas.arithmetic

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification


@SpringBootTest
class MathsterArithmeticServiceApplicationSpecIT extends Specification {

    @Autowired
    ApplicationContext context

    def "should context loads"() {
        expect:
        context != null
    }
}
