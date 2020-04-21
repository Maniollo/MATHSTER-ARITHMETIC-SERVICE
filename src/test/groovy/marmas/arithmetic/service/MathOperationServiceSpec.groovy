package marmas.arithmetic.service

import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION

class MathOperationServiceSpec extends Specification {
    AdditionFactorsService additionFactorsService = Stub()
    @Subject
    MathOperationService mathOperationService = new MathOperationService(additionFactorsService)

    def "should delegate creating factors for addition"() {
        given:
        def operationFactors = new OperationFactors(10, 20, ADDITION)
        additionFactorsService.getFactors(100) >> operationFactors

        when:
        def factors = mathOperationService.getFactorsFor(ADDITION, 100)

        then:
        factors == operationFactors
    }
}
