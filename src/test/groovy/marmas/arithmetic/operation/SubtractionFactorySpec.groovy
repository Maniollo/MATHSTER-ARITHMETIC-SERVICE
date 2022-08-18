package marmas.arithmetic.operation


import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class SubtractionFactorySpec extends Specification {

  private static final int RANGE = 10
  private static final int FAC_A = 3
  private static final int FAC_B = 2

  private FactorGenerator factorGenerator = Mock()
  @Subject
  private SubtractionFactory factory = new SubtractionFactory(factorGenerator)

  def "should return SUBTRACTION type"() {
    expect:
    factory.getType() == SUBTRACTION
  }

  def "should return subtraction factors"() {
    when:
    factorGenerator.generate(RANGE) >> FAC_A
    factorGenerator.generate(FAC_A) >> FAC_B
    def factors = factory.getFor(RANGE)

    then:
    factors == new OperationFactors(FAC_A, FAC_B, SUBTRACTION)
  }
}
