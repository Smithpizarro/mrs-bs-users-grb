package pe.business.app.users.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageBuilder {

    public static final String messageDefault="lo sentimos, no hemos podido realizar tu operación. Estamos trabajando para solucionar el inconveniente.";

    @Autowired
    private ErrorMessageDef errorMessageDef;

    public String buildMessageByCode(final String code) {
        String message = errorMessageDef.getMessage().get(code);

        if (message == null) {
            return messageDefault;
        }

        return message;
    }

}