package marmas.arithmetic.operation


import marmas.arithmetic.model.OperationFactors
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class OperationServiceSpec extends Specification {

  private final OperationGenerator generator = Mock();
  private final RandomnesProvider randomnesProvider = Mock();

  @Subject
  private OperationService operationService = new OperationService(generator, randomnesProvider)

  private final static additionFactors = new OperationFactors(2, 3, ADDITION)
  private final static subtractionFactors = new OperationFactors(1, 7, SUBTRACTION)

  def "should return factors for provided parameters"() {
    given:
    randomnesProvider.getRandomNumberFor(filtered) >> rnd
    generator.getFor(SUBTRACTION, 10) >> subtractionFactors
    generator.getFor(ADDITION, 10) >> additionFactors

        expect :
    output == operationService.getRandomFor(types, 10)

    where:
    types                             | filtered | rnd || output
    [SUBTRACTION, ADDITION]           | 2        | 1   || additionFactors
    [SUBTRACTION]                     | 1        | 0   || subtractionFactors
    [ADDITION]                        | 1        | 0   || additionFactors
    [ADDITION, SUBTRACTION, ADDITION] | 2        | 1   || subtractionFactors
    [ADDITION, SUBTRACTION, ADDITION] | 2        | 0   || additionFactors
  }

  def "should throw MissingOperationException once operation types are empty"() {
    when:
    operationService.getRandomFor([], 10)

    then:
    thrown(MissingOperationException.class)
  }

  def "should throw MissingOperationException once operation types is null"() {
    when:
    operationService.getRandomFor(null, 10)

    then:
    thrown(MissingOperationException.class)
  }

  def "should throw IncorrectMaxValueException once maxResult is not higher than 0"() {
    when:
    operationService.getRandomFor([ADDITION], 0)

    then:
    thrown(IncorrectMaxValueException.class)
  }
}
