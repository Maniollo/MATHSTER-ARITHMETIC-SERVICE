package marmas.arithmetic

import marmas.arithmetic.entity.ResultAttemptEntity
import marmas.arithmetic.model.MathOperationType
import marmas.arithmetic.repository.ResultAttemptRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
class MathsterArithmeticServiceApplicationSpecIT extends Specification {
    @Autowired
    ResultAttemptRepository resultAttemptRepository

    @Autowired
    MockMvc mvc

    def "should store result attempt"() {
        when:
        mvc.perform(MockMvcRequestBuilders.post("/results")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"operationFactors\":{\"factorA\":3,\"factorB\":5,\"operationType\":\"ADDITION\"},\"result\":8,\"isCorrect\":false}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        then:
        resultAttemptRepository.findAll() == [new ResultAttemptEntity(1, 3, 5, MathOperationType.ADDITION, 8, true)]
    }
}
