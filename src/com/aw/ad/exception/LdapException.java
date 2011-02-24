package com.aw.ad.exception;

/**
 * User: gmc
 * Date: 22/02/11
 */
public class LdapException extends RuntimeException {
    public LdapException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
