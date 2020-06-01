package marmas.arithmetic.controller


import marmas.arithmetic.service.TaskSheetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = TaskSheetExportController)
class TaskSheetExportControllerSpec extends Specification {
    @Autowired
    MockMvc mvc

    def "should return BAD REQUEST if operation type is unknown"() {
        expect:
        call("/api/tasksheet?operationTypes=DUMMY&range=10").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when operationTypse is empty"() {
        expect:
        call("/api/tasksheet?operationTypes=").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when range equals 0"() {
        expect:
        call("/api/tasksheet?operationTypes=ADDITION&range=0").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when range lower than 0"() {
        expect:
        call("/api/tasksheet?operationTypes=ADDITION&range=-1").andExpect(status().isBadRequest())
    }

    def "should return BAD REQUEST when operationType is missing"() {
        expect:
        call("/api/tasksheet").andExpect(status().isBadRequest())
    }

    private ResultActions call(String endpoint) {
        mvc.perform(get(endpoint))
    }

    @TestConfiguration
    static class OperationFactorsControllerConfig {
        private DetachedMockFactory mockFactory = new DetachedMockFactory()

        @Bean
        TaskSheetService mathOperationService() {
            mockFactory.Stub(TaskSheetService)
        }
    }
}
