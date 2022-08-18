package marmas.arithmetic.service

import spock.lang.Specification

@Deprecated
class AdditionResultAttemptRequestServiceSpec extends Specification {
    def "should verify whether attempt is correct"() {
        expect:
        new AdditionResultAttemptService().verifyAdditionAttempt(a, b, attempt) == result

        where:
        a | b | attempt || result
        1 | 2 | 3       || true
        1 | 2 | 4       || false
    }
}
