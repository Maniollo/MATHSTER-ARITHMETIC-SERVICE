package marmas.arithmetic.service


import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification

import static marmas.arithmetic.model.MathOperationType.ADDITION

class AdditionFactorsServiceSpec extends Specification {

    public static final int RANGE = 10
    public static final int FAC_A = 3
    public static final int FAC_B = 2

    def "should return factors for addition"() {
        given:
        FactorGenerator factorGenerator = Mock()
        AdditionFactorsService additionFactorsService = new AdditionFactorsService(factorGenerator)

        when:
        factorGenerator.generate(RANGE) >> FAC_A
        factorGenerator.generate(RANGE - FAC_A) >> FAC_B
        def factors = additionFactorsService.getFactors(RANGE)

        then:
        factors == new OperationFactors(FAC_A, FAC_B, ADDITION)
    }
}
