package marmas.arithmetic.service


import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import marmas.arithmetic.model.OperationFactors
import marmas.arithmetic.pdf.TaskSheetGenerator
import spock.lang.Specification
import spock.lang.Subject

import static marmas.arithmetic.model.MathOperationType.ADDITION
import static marmas.arithmetic.model.MathOperationType.SUBTRACTION

class TaskSheetServiceSpec extends Specification {
    private MathOperationService mathOperationService = Stub()
    private TaskSheetGenerator taskSheetGenerator = new TaskSheetGenerator()
    @Subject
    TaskSheetService taskSheetService = new TaskSheetService(mathOperationService, taskSheetGenerator)

    def "should create the task sheet"() {
        given:
        mathOperationService.getBulkFactors([ADDITION, SUBTRACTION], 100, 33) >> [
                new OperationFactors(48, 31, ADDITION), new OperationFactors(23, 32, ADDITION), new OperationFactors(69, 13, SUBTRACTION),
                new OperationFactors(70, 10, SUBTRACTION), new OperationFactors(98, 1, ADDITION), new OperationFactors(41, 55, ADDITION),
                new OperationFactors(4, 19, ADDITION), new OperationFactors(19, 27, ADDITION), new OperationFactors(31, 3, ADDITION),
                new OperationFactors(42, 6, SUBTRACTION), new OperationFactors(26, 66, ADDITION), new OperationFactors(28, 17, SUBTRACTION),
                new OperationFactors(98, 2, ADDITION), new OperationFactors(11, 23, ADDITION), new OperationFactors(83, 11, ADDITION),
                new OperationFactors(77, 8, SUBTRACTION), new OperationFactors(63, 28, ADDITION), new OperationFactors(68, 31, ADDITION),
                new OperationFactors(20, 41, ADDITION), new OperationFactors(74, 19, ADDITION), new OperationFactors(66, 25, ADDITION),
                new OperationFactors(90, 4, ADDITION), new OperationFactors(30, 66, ADDITION), new OperationFactors(35, 36, ADDITION),
                new OperationFactors(11, 87, ADDITION), new OperationFactors(5, 55, ADDITION), new OperationFactors(3, 69, ADDITION),
                new OperationFactors(12, 56, ADDITION), new OperationFactors(84, 4, SUBTRACTION), new OperationFactors(76, 20, SUBTRACTION),
                new OperationFactors(43, 16, SUBTRACTION), new OperationFactors(25, 26, ADDITION), new OperationFactors(5, 42, ADDITION),
        ]

        when:
        def binaryStream = taskSheetService.getAsBinaryStream([ADDITION, SUBTRACTION], 100)

        then:
        def reader = new PdfReader(binaryStream)
        reader.getNumberOfPages() == 1

        def lines = PdfTextExtractor.getTextFromPage(reader, 1)
                .toString()
                .split("\n")

        lines as String[] == ['MATHSTER Task Sheet',
                              ' ',
                              ' ',
                              ' ',
                              '48 + 31 = ............... 23 + 32 = ............... 69 - 13 = ...............',
                              '70 - 10 = ............... 98 + 1 = ............... 41 + 55 = ...............',
                              '4 + 19 = ............... 19 + 27 = ............... 31 + 3 = ...............',
                              '42 - 6 = ............... 26 + 66 = ............... 28 - 17 = ...............',
                              '98 + 2 = ............... 11 + 23 = ............... 83 + 11 = ...............',
                              '77 - 8 = ............... 63 + 28 = ............... 68 + 31 = ...............',
                              '20 + 41 = ............... 74 + 19 = ............... 66 + 25 = ...............',
                              '90 + 4 = ............... 30 + 66 = ............... 35 + 36 = ...............',
                              '11 + 87 = ............... 5 + 55 = ............... 3 + 69 = ...............',
                              '12 + 56 = ............... 84 - 4 = ............... 76 - 20 = ...............',
                              '43 - 16 = ............... 25 + 26 = ............... 5 + 42 = ...............',
                              'Copyright © 2020 MasMar'
        ] as String[]
    }
}

