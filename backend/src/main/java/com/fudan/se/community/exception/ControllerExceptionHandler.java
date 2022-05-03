package com.fudan.se.community.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/*
异常处理
 */
@Slf4j
@ControllerAdvice
@ResponseBody//表示返回的对象，Spring会自动把该对象进行json转化，最后写入到Response中。
public class ControllerExceptionHandler {
    //    400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String,String>> handleBadRequestException(BadRequestException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
//    //    403
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//    }
//
//    //    406
//    @ExceptionHandler(NotAcceptableException.class)
//    public ResponseEntity<?> handleNotAcceptableException(NotAcceptableException ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
//    }
//
//    //    202:已接受请求，但未处理完成
//    @ExceptionHandler(AcceptedException.class)
//    public ResponseEntity<?> handleCurrentUserHasRegistered(AcceptedException ex) {
//        Map<String, String> response = new HashMap<>();
//        response.put("message", ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
//    }
}
