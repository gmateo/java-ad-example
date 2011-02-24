package com.aw.ad;

import com.aw.ad.exception.DuplicateUserException;
import com.aw.ad.exception.LdapException;
import com.aw.ad.exception.PasswordStrengthException;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.OperationNotSupportedException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;

import javax.naming.directory.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: gmc
 * Date: 16/02/11
 */
public class UserDaoImpl implements UserDao {
    // Attribute names
    private static final String USER_ACCOUNT_CONTROL_ATTR_NAME = "userAccountControl";
    private static final String PASSWORD_ATTR_NAME = "unicodepwd";
    private static final String DISTINGUISHED_NAME_ATTR_NAME = "distinguishedname";
    private static final String MEMBER_ATTR_NAME = "member";

    // usercontrol params
    private static final int FLAG_TO_DISABLE_USER = 0x2;
    private static final int ADS_UF_DONT_EXPIRE_PASSWD = 0x10000;
    private static final int USER_CONTROL_NORMAL_USER = 512;

    private LdapTemplate ldapTemplate;

    public List<User> getAllUsers() {
        SearchControls controls = new SearchControls();
        controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return ldapTemplate.search("", "(objectclass=person)", controls, new UserContextMapper());
    }

    public boolean login(String userName, String password) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("cn", userName));
        return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
    }

    public void changePassword(String userName, String password) {
        try {
            ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(PASSWORD_ATTR_NAME, encodePassword(password)));
            ldapTemplate.modifyAttributes(getDnFrom(userName), new ModificationItem[]{item});
        } catch (OperationNotSupportedException e) {
            throw new PasswordStrengthException("Password:[" + password + "] does not pass the strength password validation.", e);
        } catch (Throwable e) {
            throw new LdapException("Problems changing password.", e);
        }
    }

    private byte[] encodePassword(String password) throws UnsupportedEncodingException {
        String newQuotedPassword = "\"" + password + "\"";
        return newQuotedPassword.getBytes("UTF-16LE");
    }

    public void enableUser(String userName) {
        DirContextOperations userContextOperations = ldapTemplate.lookupContext(getDnFrom(userName));
        String userAccountControlStr = userContextOperations.getStringAttribute(USER_ACCOUNT_CONTROL_ATTR_NAME);
        int newUserAccountControl = Integer.parseInt(userAccountControlStr) & ~FLAG_TO_DISABLE_USER;
        userContextOperations.setAttributeValue(USER_ACCOUNT_CONTROL_ATTR_NAME, "" + newUserAccountControl);
        ldapTemplate.modifyAttributes(userContextOperations);
    }

    public void disableUser(String userName) {
        DirContextOperations userContextOperations = ldapTemplate.lookupContext(getDnFrom(userName));
        String userAccountControlStr = userContextOperations.getStringAttribute(USER_ACCOUNT_CONTROL_ATTR_NAME);
        int newUserAccountControl = Integer.parseInt(userAccountControlStr) | FLAG_TO_DISABLE_USER;
        userContextOperations.setAttributeValue(USER_ACCOUNT_CONTROL_ATTR_NAME, "" + newUserAccountControl);
        ldapTemplate.modifyAttributes(userContextOperations);
    }


    public void createUser(User user) {
        try {
            Attributes userAttributes = new BasicAttributes();
            userAttributes.put("objectclass", "person");
            userAttributes.put("objectclass", "user");
            userAttributes.put("userPrincipalName", user.getEmailAddress());
            userAttributes.put("sAMAccountName", user.getUserName());
            userAttributes.put("givenName", user.getFirstName());
            userAttributes.put("sn", user.getLastName());
            userAttributes.put("displayName", user.getDisplayName());
            int userAccounControl = getUserAccountControl(user);
            userAttributes.put("userAccountControl", "" + userAccounControl);
            userAttributes.put("unicodepwd", encodePassword(user.getPassword()));
            ldapTemplate.bind(getDnFrom(user.getUserName()), null, userAttributes);
        } catch (NameAlreadyBoundException e) {
            throw new DuplicateUserException("User:[" + user.getUserName() + "] allready exists in AD.", e);
        } catch (OperationNotSupportedException e) {
            throw new PasswordStrengthException("Password:[" + user.getPassword() + "] does not pass the strength password validation.", e);
        } catch (Throwable e) {
            throw new LdapException("Problems creating user.", e);
        }
    }

    private int getUserAccountControl(User user) {
        int userAccounControl = USER_CONTROL_NORMAL_USER;
        if (!user.isExpirePasswd()) {
            userAccounControl |= ADS_UF_DONT_EXPIRE_PASSWD;
        }
        return userAccounControl;
    }


    public void addUserToGroup(String userName, String group) {
        try {
            DirContextAdapter dirContext = (DirContextAdapter) ldapTemplate.lookup(getDnFrom(userName));
            String dnUserFull = dirContext.getStringAttribute(DISTINGUISHED_NAME_ATTR_NAME);
            DirContextOperations groupContextOperations = ldapTemplate.lookupContext(getDnFrom(group));
            String[] currentMembers = groupContextOperations.getStringAttributes(MEMBER_ATTR_NAME);
            List<String> dnUserFullList = new ArrayList<String>();
            if (currentMembers != null && currentMembers.length > 0) {
                dnUserFullList.addAll(Arrays.asList(currentMembers));
            }
            dnUserFullList.add(dnUserFull);
            groupContextOperations.setAttributeValues(MEMBER_ATTR_NAME, dnUserFullList.toArray(new String[dnUserFullList.size()]));
            ldapTemplate.modifyAttributes(groupContextOperations);
        } catch (Throwable e) {
            throw new LdapException("Problem adding user:[" + userName + "] to Group:[" + group + "]", e);
        }
    }

    private DistinguishedName getDnFrom(String userName) {
        return new DistinguishedName("CN=" + userName);
    }

    public LdapTemplate getLdapTemplate() {
        return ldapTemplate;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
}
