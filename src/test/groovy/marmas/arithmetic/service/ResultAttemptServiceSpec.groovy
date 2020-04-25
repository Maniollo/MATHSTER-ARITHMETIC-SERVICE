package marmas.arithmetic.service

import marmas.arithmetic.exception.InvalidRequestException
import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.model.ResultAttempt
import spock.lang.Specification
import spock.lang.Subject

class ResultAttemptServiceSpec extends Specification {
    @Subject ResultAttemptService resultAttemptService = new  ResultAttemptService()

    def "should throw IllegalRequestException when flag isCorrect is already set"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(1, 2, MathOperationType.ADDITION), 123, 3, true)

        when:
        resultAttemptService.verifyResultAttempt(attempt)

        then:
        InvalidRequestException ex = thrown()
        ex.message == "Invalid request."
    }

    def "should verify correct addition attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(1, 2, MathOperationType.ADDITION), 123, 3, false)
        def expected = new ResultAttempt(new OperationFactors(1, 2, MathOperationType.ADDITION), 123, 3, true)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }

    def "should verify incorrect addition attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(1, 2, MathOperationType.ADDITION), 123, 4, false)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == attempt
    }

    def "should verify correct subtraction attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 123, 3, false)
        def expected = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 123, 3, true)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }

    def "should verify incorrect subtraction attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 123, 2, false)
        def expected = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 123, 2, false)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }
}
