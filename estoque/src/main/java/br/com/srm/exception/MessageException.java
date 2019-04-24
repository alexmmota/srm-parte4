package br.com.srm.exception;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString @EqualsAndHashCode
public class MessageException {

    private int code;
    private String messages;

}
