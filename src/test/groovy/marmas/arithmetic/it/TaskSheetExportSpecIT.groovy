package marmas.arithmetic.it

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TaskSheetExportSpecIT extends Specification {
    public static final String LINE_WITH_THREE_OPERATION_TASKS = "(\\d+\\s(\\+|\\-)\\s\\d+\\s\\=\\s.{15}\\s?){3}"
    @Autowired
    MockMvc mvc

    def "should download the task sheet"() {
        when:
        def mvcResult = mvc.perform(get("/api/tasksheet?operationTypes=ADDITION,SUBTRACTION&range=20"))
                .andExpect(status().isOk())
                .andReturn()

        then:
        MockHttpServletResponse response = mvcResult.getResponse()
        response.getHeader(CONTENT_DISPOSITION) == "attachment; filename=MathsterTasksheet.pdf"
        response.getContentType() == APPLICATION_PDF_VALUE
        def reader = new PdfReader(response.getContentAsByteArray())
        reader.getNumberOfPages() == 1
        def lines = PdfTextExtractor.getTextFromPage(reader, 1)
                .toString()
                .split("\n")
        lines[0] == 'MATHSTER Task Sheet'
        lines[1] == ' '
        lines[2] == ' '
        lines[3] == ' '
        lines[4].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[5].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[6].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[7].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[8].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[9].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[10].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[11].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[12].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[13].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[14].matches(LINE_WITH_THREE_OPERATION_TASKS)
        lines[15] == 'Copyright © 2020 MasMar'
    }

    }
