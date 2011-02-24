package com.aw.ad;

/**
 * User: gmc
 * Date: 16/02/11
 */
public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private boolean expirePasswd = true;

    private String memberOf;
    private String displayName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() {
        if (displayName == null || "".equals(displayName)) {
            displayName = userName;
        }
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }

    public String getEmailAddress() {
        if (emailAddress == null || "".equals(emailAddress)) {
            emailAddress = userName;
        }
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        if (firstName == null || "".equals(firstName)) {
            firstName = userName;
        }
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (lastName == null || "".equals(lastName)) {
            lastName = userName;
        }
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExpirePasswd() {
        return expirePasswd;
    }

    public void setExpirePasswd(boolean expirePasswd) {
        this.expirePasswd = expirePasswd;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("User");
        sb.append("{displayName='").append(displayName).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append(", memberOf='").append(memberOf).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
