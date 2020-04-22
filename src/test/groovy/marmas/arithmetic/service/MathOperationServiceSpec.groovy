package marmas.arithmetic.service

import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class MathOperationServiceSpec extends Specification {
    AdditionFactorsService additionFactorsService = Stub()
    SubtractionFactorsService subtractionFactorsService = Stub()
    @Subject
    MathOperationService mathOperationService = new MathOperationService(additionFactorsService, subtractionFactorsService)

    def "should delegate creating factors for addition"() {
        given:
        def operationFactors = new OperationFactors(10, 20, ADDITION)
        additionFactorsService.getFactors(100) >> operationFactors

        when:
        def factors = mathOperationService.getFactorsFor(ADDITION, 100)

        then:
        factors == operationFactors
    }

    def "should delegate creating factors for subtraction"() {
        given:
        def operationFactors = new OperationFactors(20, 10, SUBTRACTION)
        subtractionFactorsService.getFactors(100) >> operationFactors

        when:
        def factors = mathOperationService.getFactorsFor(SUBTRACTION, 100)

        then:
        factors == operationFactors
    }
}
