package marmas.arithmetic.service

import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification

import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class SubtractionFactorsServiceSpec extends Specification {

    public static final int RANGE = 10
    public static final int FAC_A = 3
    public static final int FAC_B = 2

    def "should return factors for subtraction"() {
        given:
        FactorGenerator factorGenerator = Stub()
        SubtractionFactorsService subtractionFactorsService = new SubtractionFactorsService(factorGenerator)

        when:
        factorGenerator.generate(RANGE) >> FAC_A
        factorGenerator.generate(FAC_A) >> FAC_B
        def factors = subtractionFactorsService.getFactors(RANGE)

        then:
        factors == new OperationFactors(FAC_A, FAC_B, SUBTRACTION)
    }
}
