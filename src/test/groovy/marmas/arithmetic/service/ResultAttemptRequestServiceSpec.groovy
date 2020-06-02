package marmas.arithmetic.service

import marmas.arithmetic.entity.ResultAttemptEntity
import marmas.arithmetic.exception.InvalidRequestException
import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.model.ResultAttemptRequest
import marmas.arithmetic.repository.ResultAttemptRepository
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class ResultAttemptRequestServiceSpec extends Specification {
    ResultAttemptRepository resultAttemptRepository = Mock()
    @Subject
    ResultAttemptService resultAttemptService = new ResultAttemptService(resultAttemptRepository)

    def "should verify correct addition attempt"() {
        given:
        def attempt = resultAttemptRequest(ADDITION, 1, 2, 3)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(ADDITION, 1, 2, 3, true)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result.correct
    }

    def "should verify incorrect addition attempt"() {
        given:
        def attempt = resultAttemptRequest(ADDITION, 1, 2, 4)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(ADDITION, 1, 2, 4, false)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        !result.correct
    }

    def "should verify correct subtraction attempt"() {
        given:
        def attempt = resultAttemptRequest(SUBTRACTION, 5, 2, 3)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(SUBTRACTION, 5, 2, 3, true)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result.correct
    }

    def "should verify incorrect subtraction attempt"() {
        given:
        def attempt = resultAttemptRequest(SUBTRACTION, 5, 2, 2)
        ResultAttemptEntity entityBeingSaved = responseAttemptEntity(SUBTRACTION, 5, 2, 2, false)
        resultAttemptRepository.save(entityBeingSaved) >> entityBeingSaved

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        !result.correct
    }

    def "should throw InvalidRequestException when operation type is missing"() {
        when:
        resultAttemptService.verifyResultAttempt(resultAttemptRequest(null, 5, 2, 2))

        then:
        InvalidRequestException ex = thrown()
        ex.message == "Operation type is missing."
    }

    private static ResultAttemptEntity responseAttemptEntity(MathOperationType operation, Integer factorA, Integer factorB, Integer result, Boolean isCorrect) {
        ResultAttemptEntity.builder()
                .correct(isCorrect)
                .factorA(factorA)
                .factorB(factorB)
                .result(result)
                .operation(operation)
                .build()
    }

    private static ResultAttemptRequest resultAttemptRequest(MathOperationType operation, Integer factorA, Integer factorB, Integer result) {
        new ResultAttemptRequest(new OperationFactors(factorA, factorB, operation), result, false, 1)
    }
}
