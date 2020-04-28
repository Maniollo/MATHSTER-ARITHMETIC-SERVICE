package marmas.arithmetic.service

import marmas.arithmetic.entity.ResultAttemptEntity
import marmas.arithmetic.exception.InvalidRequestException
import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.model.ResultAttempt
import marmas.arithmetic.repository.ResultAttemptRepository
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class ResultAttemptServiceSpec extends Specification {
    ResultAttemptRepository resultAttemptRepository = Mock()
    @Subject
    ResultAttemptService resultAttemptService = new ResultAttemptService(resultAttemptRepository)

    def "should verify correct addition attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(1, 2, ADDITION), 3, false)
        def expected = new ResultAttempt(new OperationFactors(1, 2, ADDITION), 3, true)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(true, ADDITION, 1, 2, 3)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }

    def "should verify incorrect addition attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(1, 2, ADDITION), 4, false)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(false, ADDITION, 1, 2, 4)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == attempt
    }

    def "should verify correct subtraction attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(5, 2, SUBTRACTION), 3, false)
        def expected = new ResultAttempt(new OperationFactors(5, 2, SUBTRACTION), 3, true)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(true, SUBTRACTION, 5, 2, 3)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }

    def "should verify incorrect subtraction attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(5, 2, SUBTRACTION), 2, false)
        def expected = new ResultAttempt(new OperationFactors(5, 2, SUBTRACTION), 2, false)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(false, SUBTRACTION, 5, 2, 2)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }

    private static ResultAttemptEntity responseAttemptEntity(Boolean isCorrect, MathOperationType operation, Integer factorA, Integer factorB, Integer result) {
        ResultAttemptEntity.builder()
                .correct(isCorrect)
                .factorA(factorA)
                .factorB(factorB)
                .result(result)
                .operation(operation)
                .build()
    }

    def "should throw InvalidRequestException when operation type is missing"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(5, 2, null), 2, false)

        when:
        resultAttemptService.verifyResultAttempt(attempt)

        then:
        InvalidRequestException ex = thrown()
        ex.message == "Operation type is missing."
    }
}
