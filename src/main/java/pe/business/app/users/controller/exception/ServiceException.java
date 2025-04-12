package pe.business.app.users.controller.exception;

public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    public ServiceException(String code) {
        super(code);
    }
}
