package se.lexicon.booklender.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiValidationError> details = new ArrayList<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            details.add(new ApiValidationError(error.getObjectName(), error.getField(), error.getRejectedValue(), error.getDefaultMessage()));
        }
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage("Validation Failed");
        apiError.setDebugMessage(ex.getMessage());
        apiError.setSubErrors(details);

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ApiError> dataNotfoundException(DataNotFoundException ex){
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());

        return ResponseEntity.ok(apiError);
    }

}
