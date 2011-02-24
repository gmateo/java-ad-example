package com.aw.ad.exception;

/**
 * User: gmc
 * Date: 22/02/11
 */
public class PasswordStrengthException extends LdapException {
    public PasswordStrengthException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
