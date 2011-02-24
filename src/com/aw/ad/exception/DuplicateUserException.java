package com.aw.ad.exception;

/**
 * User: gmc
 * Date: 22/02/11
 */
public class DuplicateUserException extends LdapException {

    public DuplicateUserException(String message,Throwable throwable) {
        super(message,throwable);
    }
}
