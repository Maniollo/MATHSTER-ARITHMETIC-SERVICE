package marmas.arithmetic.service


import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION

class MathOperationServiceSpec extends Specification {
    AdditionFactorsService additionFactorsService = Mock()
    @Subject
    MathOperationService mathOperationService = new MathOperationService(additionFactorsService)

    def "should delegate creating factors for addition"() {
        when:
        mathOperationService.getFactorsFor(ADDITION, 100)

        then:
        1 * additionFactorsService.getFactors(100)
    }
}
