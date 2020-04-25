package marmas.arithmetic.controller

import marmas.arithmetic.exception.InvalidRequestException
import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.model.ResultAttempt
import marmas.arithmetic.service.ResultAttemptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
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
        def request = mvc.perform(post("/results")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"userId\":123,\"result\":8,\"isCorrect\":false}"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        resultAttemptService.verifyResultAttempt(resultAttempt(false)) >> resultAttempt(true)

        and:
        noExceptionThrown()

        and:
        request.response.getContentAsString() == "{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"userId\":123,\"result\":8,\"isCorrect\":true}"
    }

    def "should return BAD REQUEST if result attempt contains set isCorrect flag"() {
        when:
        resultAttemptService.verifyResultAttempt(resultAttempt(true)) >> { throw new InvalidRequestException("Invalid request.") }

        then:
        mvc.perform(post("/results")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"userId\":123,\"result\":8,\"isCorrect\":true}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid request."))
    }

    private static ResultAttempt resultAttempt(Boolean isCorrect) {
        new ResultAttempt(new OperationFactors(3, 5, MathOperationType.ADDITION), 123, 8, isCorrect)
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
