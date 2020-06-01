package marmas.arithmetic.service

import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class MathOperationServiceSpec extends Specification {
    AdditionFactorsService additionFactorsService = Stub()
    SubtractionFactorsService subtractionFactorsService = Stub()
    FactorGenerator factorGenerator = Stub()
    @Subject
    MathOperationService mathOperationService = new MathOperationService(additionFactorsService, subtractionFactorsService, factorGenerator)

    def "should delegate creating factors for addition"() {
        given:
        def operationFactors = new OperationFactors(10, 20, ADDITION)
        additionFactorsService.getFactors(100) >> operationFactors

        when:
        def factors = mathOperationService.getFactorsFor([ADDITION], 100)

        then:
        factors == operationFactors
        and:
        0 * factorGenerator._
    }

    def "should delegate creating factors for subtraction"() {
        given:
        def operationFactors = new OperationFactors(20, 10, SUBTRACTION)
        subtractionFactorsService.getFactors(100) >> operationFactors

        when:
        def factors = mathOperationService.getFactorsFor([SUBTRACTION], 100)

        then:
        factors == operationFactors
        and:
        0 * factorGenerator._
    }

    def "should random delegate to either addition or subtraction if both provided"() {
        given:
        def subtractionFactors = new OperationFactors(20, 10, SUBTRACTION)
        def additionFactors = new OperationFactors(10, 20, ADDITION)
        subtractionFactorsService.getFactors(100) >> subtractionFactors
        additionFactorsService.getFactors(100) >> additionFactors
        factorGenerator.generate(1) >> 1 >> 0

        when:
        def factorsAttemptOne = mathOperationService.getFactorsFor([ADDITION, SUBTRACTION], 100)

        then:
        factorsAttemptOne == subtractionFactors

        when:
        def factorsAttemptTwo = mathOperationService.getFactorsFor([ADDITION, SUBTRACTION], 100)

        then:
        factorsAttemptTwo == additionFactors
    }

    def "should bulk generate operations"() {
        given:
        additionFactorsService.getFactors(100) >> new OperationFactors(10, 20, ADDITION)

        when:
        def factors = mathOperationService.getBulkFactors([ADDITION], 100, 20)

        then:
        factors.size() == 20
        factors.each {
            assert it.factorA == 10
            assert it.factorB == 20
            assert it.operationType == ADDITION
        }

    }
}
