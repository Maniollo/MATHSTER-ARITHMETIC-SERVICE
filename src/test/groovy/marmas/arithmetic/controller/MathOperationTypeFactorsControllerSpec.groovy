package marmas.arithmetic.controller

import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.service.MathOperationService
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = MathOperationController)
class MathOperationTypeFactorsControllerSpec extends Specification {
    @Autowired
    MockMvc mvc

    @Autowired
    MathOperationService mathOperationService

    def "should return operation data for addition"() {
        when: 'calling getOperationFactors for addition with numeric range 10'
        def mvcResult = call("/operation?operationTypes=ADDITION&range=10")
                .andExpect(status().is2xxSuccessful())
                .andReturn()

        then: 'operation service return factors'
        mathOperationService.getFactorsFor([ADDITION], 10) >> new OperationFactors(3, 5, ADDITION)

        noExceptionThrown()

        when: 'inspecting the content'
        def content = mvcResult.response.getContentAsString()

        then: 'result contains addition factors and operation type'
        content == "{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"}"
    }

    def "should return operation data for addition with default range 10 if not provided"() {
        when: 'calling getOperationFactors for addition with numeric range 10'
        def mvcResult = mvc.perform(get("/operation?operationTypes=ADDITION"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()

        then: 'operation service return factors'
        mathOperationService.getFactorsFor([ADDITION], 10) >> new OperationFactors(3, 5, ADDITION)

        noExceptionThrown()

        when: 'inspecting the content'
        def content = mvcResult.response.getContentAsString()

        then: 'result contains addition factors and operation type'
        content == "{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"}"
    }

    def "should return operation data for subtraction"() {
        when: 'calling getOperationFactors for subtraction with numeric range 10'
        def mvcResult = call("/operation?operationTypes=SUBTRACTION&range=10")
                .andExpect(status().is2xxSuccessful())
                .andReturn()

        then: 'operation service return factors'
        mathOperationService.getFactorsFor([SUBTRACTION], 10) >> new OperationFactors(5, 3, SUBTRACTION)

        noExceptionThrown()

        when: 'inspecting the content'
        def content = mvcResult.response.getContentAsString()

        then: 'result contains addition factors and operation type'
        content == "{\"factorA\":5,\"factorB\":3,\"operationType\":\"SUBTRACTION\"}"
    }

    def "should return operation data for subtraction with default range 10 if not provided"() {
        when: 'calling getOperationFactors for subtraction with numeric range 10'
        def mvcResult = mvc.perform(get("/operation?operationTypes=SUBTRACTION"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()

        then: 'operation service return factors'
        mathOperationService.getFactorsFor([SUBTRACTION], 10) >> new OperationFactors(5, 3, SUBTRACTION)

        noExceptionThrown()

        when: 'inspecting the content'
        def content = mvcResult.response.getContentAsString()

        then: 'result contains addition factors and operation type'
        content == "{\"factorA\":5,\"factorB\":3,\"operationType\":\"SUBTRACTION\"}"
    }

    def "should remove duplication of operation"() {
        when: 'calling getOperationFactors for subtraction with numeric range 10'
        def mvcResult = mvc.perform(get("/operation?operationTypes=SUBTRACTION,SUBTRACTION"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()

        then: 'operation service return factors'
        mathOperationService.getFactorsFor([SUBTRACTION], 10) >> new OperationFactors(5, 3, SUBTRACTION)

        noExceptionThrown()

        when: 'inspecting the content'
        def content = mvcResult.response.getContentAsString()

        then: 'result contains addition factors and operation type'
        content == "{\"factorA\":5,\"factorB\":3,\"operationType\":\"SUBTRACTION\"}"
    }

    def "should return operation data for either subtraction or addition if both provided"() {
        when: 'calling getOperationFactors for subtraction and addition with numeric range 10'
        def mvcResult = mvc.perform(get("/operation?operationTypes=SUBTRACTION,ADDITION"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()

        then: 'operation service return factors'
        mathOperationService.getFactorsFor([SUBTRACTION, ADDITION], 10) >> new OperationFactors(5, 3, SUBTRACTION)

        noExceptionThrown()

        when: 'inspecting the content'
        def content = mvcResult.response.getContentAsString()

        then: 'result contains addition factors and operation type'
        content == "{\"factorA\":5,\"factorB\":3,\"operationType\":\"SUBTRACTION\"}"
    }

    def "should return BAD REQUEST if operation type is unknown"() {
        expect:
        call("/operation?operationTypes=DUMMY&range=10").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when operationTypse is empty"() {
        expect:
        call("/operation?operationTypes=").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when range equals 0"() {
        expect:
        call("/operation?operationTypes=ADDITION&range=0").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when range lower than 0"() {
        expect:
        call("/operation?operationTypes=ADDITION&range=-1").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when operationType is missing"() {
        expect:
        call("/operation").andExpect(status().isBadRequest())
    }

    private ResultActions call(String endpoint) {
        mvc.perform(get(endpoint))
    }

    @TestConfiguration
    static class OperationFactorsControllerConfig {
        private DetachedMockFactory mockFactory = new DetachedMockFactory()

        @Bean
        MathOperationService mathOperationService() {
            mockFactory.Stub(MathOperationService)
        }
    }
}
