package br.com.luizalabs.wishlist.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.List;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
@Getter
public class BusinessException extends RuntimeException{

    private final List<String> messages;

    public BusinessException(Throwable cause) {
        this(cause, Collections.emptyList());
    }

    public BusinessException(List<String> messages) {
        this(null, messages);
    }

    public BusinessException(String message){
        this(null, Collections.singletonList(message));
    }
    private BusinessException(Throwable cause, List<String> messages) {
        super(String.join("\n", messages), cause);
        this.messages = messages;
    }
}
