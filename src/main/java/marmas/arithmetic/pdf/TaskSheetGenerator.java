package marmas.arithmetic.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.AllArgsConstructor;
import marmas.arithmetic.exception.TaskSheetException;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_LEFT;
import static com.itextpdf.text.Element.ALIGN_RIGHT;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.FontFamily.TIMES_ROMAN;
import static com.itextpdf.text.Font.NORMAL;

@Component
@AllArgsConstructor
public class TaskSheetGenerator {
    private static final Font TITLE_PAGE_FONT = new Font(TIMES_ROMAN, 24, BOLD);
    private static final Font CELL_FONT = new Font(TIMES_ROMAN, 20, BOLD);
    private static final Font CELL_FONT_DOTS = new Font(TIMES_ROMAN, 8, BOLD);
    private static final Font FOOTER_FONT = new Font(TIMES_ROMAN, 12, NORMAL);
    private static final String TASK_SHEET_TITLE = "MATHSTER Task Sheet";
    private static final String AUTHOR = "MasMar";

    public byte[] asBinaryContent(List<OperationFactors> operationFactors) {
        try {
            Document document = new Document();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            addMetaData(document);
            addHeader(document);
            addContent(document, operationFactors);
            addFooter(document);

            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            throw new TaskSheetException("Generating Task Sheet failed", e);
        }
    }

    private void addMetaData(Document document) {
        document.addTitle(TASK_SHEET_TITLE);
        document.addKeywords(TASK_SHEET_TITLE);
        document.addAuthor(AUTHOR);
        document.addCreator(AUTHOR);
    }

    private void addHeader(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        Paragraph titlePage = new Paragraph(TASK_SHEET_TITLE, TITLE_PAGE_FONT);
        titlePage.setAlignment(ALIGN_CENTER);
        preface.add(titlePage);
        addEmptyLine(preface, 1);
        preface.add(new LineSeparator());
        addEmptyLine(preface, 2);
        document.add(preface);
    }

    private void addContent(Document document, List<OperationFactors> operationFactors) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(90);
        table.setHorizontalAlignment(ALIGN_LEFT);

        operationFactors.forEach(it -> {
            Phrase operation = new Phrase(new Chunk(it.getFactorA() + " " + it.getOperationType().getSign() + " " + it.getFactorB() + " = ", CELL_FONT));
            PdfPCell cell = new PdfPCell(operation);
            cell.setVerticalAlignment(ALIGN_CENTER);
            cell.setHorizontalAlignment(ALIGN_RIGHT);
            cell.setBorderWidth(0);
            cell.setFixedHeight(60);
            Chunk dots = new Chunk(".".repeat(15), CELL_FONT_DOTS);
            operation.add(dots);
            table.addCell(cell);
        });

        document.add(table);
    }

    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph();
        footer.setAlignment(ALIGN_CENTER);
        footer.add(new Paragraph("Copyright \u00A9 2020 MasMar", FOOTER_FONT));
        document.add(footer);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
