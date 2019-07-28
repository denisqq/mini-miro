package com.denisqq.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4271320740076092506L;
    private String message;
}
