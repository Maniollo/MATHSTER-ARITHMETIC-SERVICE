package marmas.arithmetic.service

import marmas.arithmetic.exception.InvalidRequestException
import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.model.ResultAttempt
import spock.lang.Specification
import spock.lang.Subject

class ResultAttemptServiceSpec extends Specification {
    @Subject
    ResultAttemptService resultAttemptService = new ResultAttemptService()

    def "should verify correct addition attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(1, 2, MathOperationType.ADDITION), 3, false)
        def expected = new ResultAttempt(new OperationFactors(1, 2, MathOperationType.ADDITION), 3, true)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }

    def "should verify incorrect addition attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(1, 2, MathOperationType.ADDITION), 4, false)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == attempt
    }

    def "should verify correct subtraction attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 3, false)
        def expected = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 3, true)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
    }

    def "should verify incorrect subtraction attempt"() {
        given:
        def attempt = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 2, false)
        def expected = new ResultAttempt(new OperationFactors(5, 2, MathOperationType.SUBTRACTION), 2, false)

        when:
        def result = resultAttemptService.verifyResultAttempt(attempt)

        then:
        result == expected
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
