package marmas.arithmetic.controller

import marmas.arithmetic.exception.InvalidRequestException
import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.model.ResultAttemptRequest
import marmas.arithmetic.model.ResultAttemptResponse
import marmas.arithmetic.service.ResultAttemptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = ResultAttemptController)
class ResultAttemptControllerSpec extends Specification {

    @Autowired
    ResultAttemptService resultAttemptService

    @Autowired
    MockMvc mvc

    def "should return verified result attempt"() {
        when:
        def request = call("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"result\":8,\"isCorrect\":false}")
                .andExpect(status().isOk())
                .andReturn()

        then:
        resultAttemptService.verifyResultAttempt(resultAttempt(false, 8)) >> new ResultAttemptResponse(true,)

        and:
        noExceptionThrown()

        and:
        request.response.getContentAsString() == "{\"correct\":true}"
    }

    def "should return BAD REQUEST if result attempt contains set isCorrect flag"() {
        when:
        resultAttemptService.verifyResultAttempt(resultAttempt(true, 8)) >> { throw new InvalidRequestException("Invalid request.") }

        then:
        call("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"result\":8,\"correct\":true}")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"errors\":[\"Flag isCorrect cannot be set\"],\"errorMessage\":\"Validation failed. 1 error(s)\"}"))
    }

    def "should return BAD REQUEST when result attempt has missing parameters"() {
        expect:
        call("{\"userId\":123,\"result\":8,\"isCorrect\":false}")
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"errors\":[\"Operation Factors must be provided\"],\"errorMessage\":\"Validation failed. 1 error(s)\"}"))
    }

    def "should return verified result attempt when result is missing"() {
        when:
        def request = call("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"isCorrect\":false}")
                .andExpect(status().isOk())
                .andReturn()

        then:
        resultAttemptService.verifyResultAttempt(resultAttempt(false, 0)) >> new ResultAttemptResponse(false)

        and:
        noExceptionThrown()

        and:
        request.response.getContentAsString() == "{\"correct\":false}"
    }

    private ResultActions call(String content) {
        mvc.perform(post("/results")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(content))
    }

    private static ResultAttemptRequest resultAttempt(Boolean isCorrect, Integer result) {
        new ResultAttemptRequest(new OperationFactors(3, 5, MathOperationType.ADDITION), result, isCorrect)
    }

    @TestConfiguration
    static class ResultAttemptControllerConfig {
        private DetachedMockFactory mockFactory = new DetachedMockFactory()

        @Bean
        ResultAttemptService resultAttemptService() {
            mockFactory.Stub(ResultAttemptService)
        }
    }
}
