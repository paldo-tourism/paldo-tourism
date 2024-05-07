package com.estsoft.paldotourism.exception;

import com.estsoft.paldotourism.exception.bus.TerminalNotFoundException;
import com.estsoft.paldotourism.exception.likes.AlreadyLikedException;
import com.estsoft.paldotourism.exception.openapi.BusRouteNotFoundException;
import com.estsoft.paldotourism.exception.openapi.UrlNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    //메인화면에서 발생할 수 있는 에러
    @ExceptionHandler(TerminalNotFoundException.class)
    public ResponseEntity<ErrorForm> TerminalNotFoundException(TerminalNotFoundException e) {
        ErrorForm errorForm =  new ErrorForm(HttpStatus.NOT_FOUND.value(),"존재하지 않는 터미널입니다.");
        return new ResponseEntity<>(errorForm, HttpStatus.NOT_FOUND);
    }

    //Open API에서 발생할 수 있는 에러
    @ExceptionHandler(UrlNotValidException.class)
    public ErrorForm UrlNotValidException(UrlNotValidException e) {
        return new ErrorForm(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 URL 입니다.");
    }

    @ExceptionHandler(BusRouteNotFoundException.class)
    public ResponseEntity<ErrorForm> BusRouteNotFoundException(BusRouteNotFoundException e) {
        //애초에 사용자가 출발지를 눌렀을때 갈 수 있는 도착지만 뜨게하면 좋을텐데 우리는 버스DB에 정보가 충분하지 않아서 불가능
        ErrorForm errorForm = new ErrorForm(HttpStatus.NOT_FOUND.value(), "존재하지 않는 버스 노선입니다.");
        return new ResponseEntity<>(errorForm, HttpStatus.NOT_FOUND);
    }

    //찜 관련 에러
    @ExceptionHandler(AlreadyLikedException.class)
    public String AlreadyLikedException(AlreadyLikedException e, RedirectAttributes redirectAttributes) {
        ErrorForm errorForm = new ErrorForm(HttpStatus.BAD_REQUEST.value(),"해당 노선은 이미 찜 목록에 추가되어 있습니다.");
        redirectAttributes.addFlashAttribute("error",errorForm);
        return "redirect:/timeTable";
    }

}
