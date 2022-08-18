package marmas.arithmetic.operation


import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class OperationGeneratorSpec extends Specification {
  private final AdditionFactory additionFactory = Mock()
  private final SubtractionFactory subtractionFactory = Mock()

  @Subject
  private final generator = new OperationGenerator([additionFactory, subtractionFactory])
  private final static additionFactors = new OperationFactors(2, 3, ADDITION)
  private final static subtractionFactors = new OperationFactors(1, 7, SUBTRACTION)

  def "should generate operation factors for #operation"() {
    given:
    additionFactory.getFor(10) >> additionFactors
    additionFactory.getType() >> ADDITION
    subtractionFactory.getFor(10) >> subtractionFactors
    subtractionFactory.getType() >> SUBTRACTION

    expect:
    factors == generator.getFor(operation, 10)

    where:
    operation   || factors
    ADDITION    || additionFactors
    SUBTRACTION || subtractionFactors
  }

  def "should throw UnsupportedOperation exception once operation is not supported"() {
    when:
    new OperationGenerator([subtractionFactory]).getFor(ADDITION, 10)

    then:
    thrown(NotSupportedOperationException.class)
  }
}
