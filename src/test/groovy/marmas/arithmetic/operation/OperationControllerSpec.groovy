package marmas.arithmetic.operation

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

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = OperationController)
class OperationControllerSpec extends Specification {

  private static final String BASE_URL = "/api/operations"

  @Autowired
  MockMvc mvc

  @Autowired
  OperationService operationService

  @Autowired
  ResultAttemptService resultAttemptService

  def "should return response for single operation type"() {
    when: 'calling getOperationFactors for addition with numeric range 10'
    def mvcResult = callGet("?types=ADDITION&max=10")
        .andExpect(status().is2xxSuccessful())
        .andReturn()

    then: 'operation service return factors'
    operationService.getRandomFor([ADDITION], 10) >> new OperationFactors(3, 5, ADDITION)

    noExceptionThrown()

    when: 'inspecting the content'
    def content = mvcResult.response.getContentAsString()

    then: 'result contains addition factors and operation type'
    content == "{\"operations\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"}}"
  }

  def "should return response for single operation where maxresult is not provided and defaulted to 10 due to it"() {
    when: 'calling getOperationFactors for addition with numeric range 10'
    def mvcResult = callGet("?types=ADDITION")
        .andExpect(status().is2xxSuccessful())
        .andReturn()

    then: 'operation service return factors'
    operationService.getRandomFor([ADDITION], 10) >> new OperationFactors(3, 5, ADDITION)

    noExceptionThrown()

    when: 'inspecting the content'
    def content = mvcResult.response.getContentAsString()

    then: 'result contains addition factors and operation type'
    content == "{\"operations\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"}}"
  }

  def "should return response for list of provided types"() {
    when: 'calling getOperationFactors for addition with numeric range 10'
    def mvcResult = callGet("?types=ADDITION,SUBTRACTION&max=20")
        .andExpect(status().is2xxSuccessful())
        .andReturn()

    then: 'operation service return factors'
    operationService.getRandomFor([ADDITION, SUBTRACTION], 20) >> new OperationFactors(3, 5, ADDITION)

    noExceptionThrown()

    when: 'inspecting the content'
    def content = mvcResult.response.getContentAsString()

    then: 'result contains addition factors and operation type'
    content == "{\"operations\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"}}"
  }

  def "should return BAD REQUEST if operation type is unknown"() {
    expect:
    callGet("?types=DUMMY&max=10").andExpect(status().isBadRequest())
  }

  def "should return BAD REQUEST when operationTypes is empty"() {
    expect:
    callGet("?types=").andExpect(status().isBadRequest())
  }

  def "should return BAD REQUEST when range lower than 10"() {
    expect:
    callGet("?types=ADDITION&max=-1").andExpect(status().isBadRequest())
  }

  def "should return BAD REQUEST when operationType is missing"() {
    expect:
    callGet("").andExpect(status().isBadRequest())
  }

  def "should return the result of answer verification"() {
    when:
    def request = callPost("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"result\":8,\"attemptNumber\":1}")
        .andExpect(status().isOk())
        .andReturn()

    then:
    resultAttemptService.verifyResultAttempt(resultAttempt(8)) >> new ResultAttemptResponse(true,)

    and:
    noExceptionThrown()

    and:
    request.response.getContentAsString() == "{\"correct\":true}"
  }

  def "should return verified result attempt when result is missing"() {
    when:
    def request = callPost("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"attemptNumber\":1}")
        .andExpect(status().isOk())
        .andReturn()

    then:
    resultAttemptService.verifyResultAttempt(resultAttempt(0)) >> new ResultAttemptResponse(false)

    and:
    noExceptionThrown()

    and:
    request.response.getContentAsString() == "{\"correct\":false}"
  }

  private static ResultAttemptRequest resultAttempt(Integer result) {
    new ResultAttemptRequest(new OperationFactors(3, 5, ADDITION), result, 1)
  }

  private ResultActions callPost(String content) {
    mvc.perform(post(BASE_URL)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON)
        .content(content))
  }

  private ResultActions callGet(String endpoint) {
    mvc.perform(get(BASE_URL + endpoint))
  }

  @TestConfiguration
  static class OperationControllerSpecConfig {
    private DetachedMockFactory mockFactory = new DetachedMockFactory()

    @Bean
    OperationService mathOperationService() {
      mockFactory.Stub(OperationService)
    }

    @Bean
    ResultAttemptService resultAttemptService() {
      mockFactory.Stub(ResultAttemptService)
    }
  }
}
