package marmas.arithmetic.operation


import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION

class AdditionFactorySpec extends Specification {

  private static final int RANGE = 10
  private static final int FAC_A = 3
  private static final int FAC_B = 2

  FactorGenerator factorGenerator = Mock()
  @Subject
  AdditionFactory factory = new AdditionFactory(factorGenerator)

  def "should return ADDITION type"() {
    expect:
    factory.getType() == ADDITION
  }

  def "should return addition factors"() {
    when:
    factorGenerator.generate(RANGE) >> FAC_A
    factorGenerator.generate(RANGE - FAC_A) >> FAC_B
    def factors = factory.getFor(RANGE)

    then:
    factors == new OperationFactors(FAC_A, FAC_B, ADDITION)
  }
}
