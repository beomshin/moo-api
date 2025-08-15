package com.kr.moo.aop;

import com.kr.moo.exception.MooGlobalException;
import com.kr.moo.exception.SeatException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = com.kr.moo.controller.SeatController.class)
@RequiredArgsConstructor
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = SeatException.class)
    public ResponseEntity<?> handle(SeatException e) { // 파라미터 오류 글로벌 처리
        log.error("■ 응답 오류 [SeatException]");
        return ResponseEntity.ok().body(Map.of(
                "resultCode", e.getResultCode(),
                "resultMsg", e.getResultMsg()
        ));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) { // 파라미터 오류 글로벌 처리
        log.error("■ 응답 오류 [MethodArgumentNotValidException]");
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "파라미터 오류"
        ));
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<?> handle(BindException e) { // 파라미터 바인딩 오류 글로벌 처리
        log.error("■ 응답 오류 [BindException]");
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "파라미터 바인딩 오류"
        ));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> handle(HttpMessageNotReadableException e) { // 자료형 오류 글로벌 처리
        log.error("■ 응답 오류 [HttpMessageNotReadableException]", e);
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "파라미터 자료형 오류"
        ));
    }

    @ExceptionHandler(value = MissingRequestHeaderException.class)
    public ResponseEntity<?> handle(MissingRequestHeaderException e) { // 헤더 누락 오류 글로벌 처리
        log.error("■ 응답 오류 [MissingRequestHeaderException]");
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "헤더 누락 오류"
        ));    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<?> handle(MissingServletRequestParameterException e) { // 파라미터 누락 오류 글로벌 처리
        log.error("■ 응답 오류 [MissingServletRequestParameterException]");
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "파라미터 누락 오류"
        ));    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handle(MethodArgumentTypeMismatchException e) { // 파라미터 검증 오류 글로벌 처리
        log.error("■ 응답 오류 [MethodArgumentTypeMismatchException]");
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "파라미터 검증 오류"
        ));    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<?> handle(IOException e) { // IO Exception 글로벌 처리
        log.error("■ 응답 오류 [IOException]: ", e);
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "IO 오류"
        ));
    }

    @ExceptionHandler(value = MooGlobalException.class)
    public ResponseEntity<?> handle(MooGlobalException e) {
        log.error("■ 응답 오류 [MooGlobalException]", e);
        return ResponseEntity.ok().body(Map.of(
                "resultCode", e.getResultCode(),
                "resultMsg", e.getResultMsg()
        ));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        log.error("■ 응답 오류 [Exception]", e);
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "미지정오류"
        ));
    }


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> handle(RuntimeException e) { // 런타임 오류 글로벌 처리
        log.error("■ 응답 오류 [RuntimeException]", e);
        return ResponseEntity.ok().body(Map.of(
                "resultCode", "9999",
                "resultMsg", "미지정오류"
        ));
    }


}
