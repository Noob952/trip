package cn.wolfcode.trip.exception;

/**
 *  自定义逻辑异常， 给用户看的异常信息
 */
public class LogicException extends RuntimeException {
    public LogicException(String message) {
        super(message);
    }
}
