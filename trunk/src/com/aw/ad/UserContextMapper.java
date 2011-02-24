package com.aw.ad;


import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.support.AbstractContextMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;


/**
 * User: gmc
 * Date: 16/02/11
 */
public class UserContextMapper extends AbstractContextMapper {
    @Override
    protected Object doMapFromContext(DirContextOperations context) {
        User user = new User();
        user.setUserName(context.getStringAttribute("cn"));
        user.setFirstName(context.getStringAttribute("givenName"));
        user.setLastName(context.getStringAttribute("sn"));
        user.setEmailAddress(context.getStringAttribute("userPrincipalName"));
        user.setMemberOf(context.getStringAttribute("memberOf"));
        user.setDisplayName(context.getStringAttribute("displayName"));
        return user;
    }
}