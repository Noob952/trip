package cn.wolfcode.trip.advice;

import cn.wolfcode.trip.exception.LogicException;
import cn.wolfcode.trip.util.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(LogicException.class)
    @ResponseBody
    public JsonResult handlerLoginEx(LogicException e, HttpServletResponse resp) {
        e.printStackTrace();
        resp.setContentType("application/json;charset=utf-8");
        return JsonResult.error(JsonResult.CODE_ERROR_PARAM, e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult handlerException(LogicException e, HttpServletResponse resp) {
        e.printStackTrace();
        resp.setContentType("application/json;charset=utf-8");
        return JsonResult.defaultError();
    }

}
