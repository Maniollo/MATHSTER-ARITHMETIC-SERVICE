package marmas.arithmetic.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.service.TaskSheetService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayInputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@AllArgsConstructor
@Validated
@Api(value = "TaskSheetExportController")
class TaskSheetExportController {

    private final TaskSheetService taskSheetService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Service Error")
    })
    @ApiOperation(value = "Return the task sheet with chosen operations", response = Boolean.class)
    @GetMapping("/api/tasksheet")
    ResponseEntity<InputStreamResource> getTaskSheet(
            @NotNull @NotEmpty @RequestParam List<MathOperationType> operationTypes,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer range) {

        ByteArrayInputStream taskSheet = taskSheetService.getAsBinaryStream(operationTypes.stream().distinct().collect(toList()), range);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=MathsterTasksheet.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(new InputStreamResource(taskSheet));
    }
}
