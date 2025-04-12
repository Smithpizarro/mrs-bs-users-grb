package pe.business.app.users.controller.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pe.business.app.users.model.ErrorMessageT;
import pe.business.app.users.model.TransactionRs;
import pe.business.app.users.util.ErrorMessageBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class SharedExceptionHandler {

    public static final String MESSAGE_BAD_REQUEST="Los datos ingresados son invalidos.";

    public static final String MESSAGE_GENERIC_SERVER="Lo sentimos, estamos solucionando el problema.";

    @Autowired
    private ErrorMessageBuilder errorMessageBuilder;


    // validation type value
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<TransactionRs<Object>> handleIllegalArgumentException(final IllegalArgumentException ex)throws IOException {
        TransactionRs<Object> response = new TransactionRs<>();
        response.setDescripcion(MESSAGE_BAD_REQUEST+ 1);

        ErrorMessageT errorMessageDef = ErrorMessageT.builder()
                .code("01")
                .messages(Arrays.asList(ex.getMessage())).build();
        response.setRespuesta(errorMessageDef);
        return new ResponseEntity<TransactionRs<Object>>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<TransactionRs<Object>> handleMethodArgumentException( MethodArgumentNotValidException  ex)throws IOException {
        TransactionRs<Object> response = new TransactionRs<>();
        response.setDescripcion(MESSAGE_BAD_REQUEST+ 2);

        List<String> errors =(ex.getBindingResult().getFieldErrors()).stream()
                .map(err ->{
                    return err.getField()+" , "+ err.getDefaultMessage();
                }).collect(Collectors.toList());
        /*List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());*/

        ErrorMessageT errorMessageDef = ErrorMessageT.builder()
                .code("02")
                .messages(errors).build();
        response.setRespuesta(errorMessageDef);
        return new ResponseEntity<TransactionRs<Object>>(response, HttpStatus.BAD_REQUEST);

    }

    //Validated RequestParams or PathVariables

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<TransactionRs<Object>> handleAppException(ConstraintViolationException ex)throws IOException {
        TransactionRs<Object> response = new TransactionRs<>();
        response.setDescripcion(MESSAGE_BAD_REQUEST +3);
        String errorMessage = new ArrayList<>(ex.getConstraintViolations()).get(0).getMessage();

        List<String> errors =(ex.getConstraintViolations()).stream()
                .map(err ->{
                    return err.getMessage();
                }).collect(Collectors.toList());
        ErrorMessageT errorMessageDef = ErrorMessageT.builder()
                .code("03")
                .messages(errors).build();
        response.setRespuesta(errorMessageDef);
        return new ResponseEntity<TransactionRs<Object>>(response, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<TransactionRs<String>> handleServiceException(HttpServletRequest req, ServiceException ex) {

        String code = ex.getMessage();
        TransactionRs<String> response = new TransactionRs<>();
        if(code!=null){
            String	msj = errorMessageBuilder.buildMessageByCode(code);
            response.setDescripcion(msj);
            response.setRespuesta(null);
        }else{
            String msj = errorMessageBuilder.buildMessageByCode("7002");
            response.setDescripcion(msj);
            response.setRespuesta(null);
        }
        return new ResponseEntity<TransactionRs<String>>(response,  HttpStatus.CONFLICT);
    }


    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<TransactionRs<String>> handleAuthenticationException(Exception ex) {
        TransactionRs<String> response = new TransactionRs<>();
        String	msj = errorMessageBuilder.buildMessageByCode("7006");
        response.setDescripcion(msj);
        response.setRespuesta(null);
        return new ResponseEntity<TransactionRs<String>>(response,  HttpStatus.UNAUTHORIZED);
    }

}
