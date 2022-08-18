package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.operation.OperationService;
import marmas.arithmetic.pdf.TaskSheetGenerator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TaskSheetService {
  private final OperationService operationService;
  private final TaskSheetGenerator taskSheetGenerator;

  public ByteArrayInputStream getAsBinaryStream(List<MathOperationType> operationTypes, Integer range) {
    log.info("Generating Task Sheet started.");
    byte[] binaryTaskSheet = taskSheetGenerator.asBinaryContent(operationService.getRandomNTimesFor(operationTypes, range, 33));
    log.info("Generating Task Sheet finished.");
    return new ByteArrayInputStream(binaryTaskSheet);
  }
}
