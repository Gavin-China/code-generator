package com.gavin.generator.exception;

import com.baomidou.dynamic.datasource.exception.CannotFindDataSourceException;
import com.gavin.generator.domin.AjaxResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Gavin Lin
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResult validExceptionHandle(MethodArgumentNotValidException exception){
        exception.printStackTrace();
        BindingResult result = exception.getBindingResult();
        StringBuilder errorMsg = new StringBuilder() ;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> {
                System.out.println("Field:" + error.getField() + ", Msg:" + error.getDefaultMessage());
                errorMsg.append(error.getDefaultMessage()).append("\n");
            });
        }
        return AjaxResult.error(errorMsg.toString());
    }

    @ExceptionHandler(value = CannotFindDataSourceException.class)
    @ResponseBody
    public AjaxResult cannotFindDataSourceExceptionHandle(CannotFindDataSourceException exception){
        exception.printStackTrace();
        return AjaxResult.error("未链接数据源！");
    }
}