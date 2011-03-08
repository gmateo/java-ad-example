package com.aw.ad;

import java.util.Date;

/**
 * User: gmc
 * Date: 07/03/11
 */
public class ADUserDetails implements UserDetails {
    private String username;
    private String dn;
    private boolean accountNonExpired;
    private boolean accountNeverExpire;
    private boolean credentialsHasToBeChangedAtFirst;
    private boolean credentialsNeverExpire;
    private boolean credentialsNonExpired;
    private boolean enabled = true;
    private Date accountExpiration;
    private Date credentialsExpiration;


    private int timeBeforeAccountExpiration = Integer.MAX_VALUE;
    private int timeBeforeCredentialsExpiration = Integer.MAX_VALUE;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean accountNeverExpire() {
        return accountNeverExpire;
    }

    public void setAccountNeverExpire(boolean accountNeverExpire) {
        this.accountNeverExpire = accountNeverExpire;
    }

    public boolean credentialsHasToBeChangedAtFirst() {
        return credentialsHasToBeChangedAtFirst;
    }

    public void setCredentialsHasToBeChangedAtFirst(boolean credentialsHasToBeChangedAtFirst) {
        this.credentialsHasToBeChangedAtFirst = credentialsHasToBeChangedAtFirst;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setTimeBeforeAccountExpiration(int timeBeforeAccountExpiration) {
        this.timeBeforeAccountExpiration = timeBeforeAccountExpiration;
    }

    public boolean credentialsNeverExpire() {
        return credentialsNeverExpire;
    }

    public void setCredentialsNeverExpire(boolean credentialsNeverExpire) {
        this.credentialsNeverExpire = credentialsNeverExpire;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setTimeBeforeCredentialsExpiration(int timeBeforeCredentialsExpiration) {
        this.timeBeforeCredentialsExpiration = timeBeforeCredentialsExpiration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ADUserDetails");
        sb.append("{username='").append(username).append('\'');
        sb.append(", dn='").append(dn).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append(", accountNeverExpire=").append(accountNeverExpire);
        sb.append(", accountNonExpired=").append(accountNonExpired);
        sb.append(", accountExpiration=").append(accountExpiration);
        sb.append(", timeBeforeAccountExpiration=").append(timeBeforeAccountExpiration);
        sb.append(", credentialsHasToBeChangedAtFirst=").append(credentialsHasToBeChangedAtFirst);
        sb.append(", credentialsNeverExpire=").append(credentialsNeverExpire);
        sb.append(", credentialsNonExpired=").append(credentialsNonExpired);
        sb.append(", credentialsExpiration=").append(credentialsExpiration);
        sb.append(", timeBeforeCredentialsExpiration=").append(timeBeforeCredentialsExpiration);
        sb.append('}');
        return sb.toString();
    }

    public void setAccountExpiration(Date accountExpiration) {
        this.accountExpiration = accountExpiration;
    }

    public void setCredentialsExpiration(Date credentialsExpiration) {
        this.credentialsExpiration = credentialsExpiration;
    }
}
