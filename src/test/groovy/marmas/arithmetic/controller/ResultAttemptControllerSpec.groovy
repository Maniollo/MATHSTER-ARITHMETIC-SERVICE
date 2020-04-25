package marmas.arithmetic.controller

import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.model.ResultAttempt
import marmas.arithmetic.service.ResultAttemptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@WebMvcTest(controllers = ResultAttemptController)
class ResultAttemptControllerSpec extends Specification {

    @Autowired
    ResultAttemptService resultAttemptService

    @Autowired
    MockMvc mvc

    def "should return verified result attempt"() {
        when:
        def request = mvc.perform(
                MockMvcRequestBuilders.post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"userId\":123,\"result\":8,\"isCorrect\":false}")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()

        then:
        resultAttemptService.verifyResultAttempt(new ResultAttempt(new OperationFactors(3, 5, MathOperationType.ADDITION), 123, 8, false)) >> new ResultAttempt(new OperationFactors(3, 5, MathOperationType.ADDITION), 123, 8, true)

        and:
        noExceptionThrown()

        and:
        request.response.getContentAsString() == "{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"userId\":123,\"result\":8,\"isCorrect\":true}"
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
