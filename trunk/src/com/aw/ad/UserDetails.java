package com.aw.ad;

/**
 * User: gmc
 * Date: 05/03/11
 */
public interface UserDetails {
    /**
     * Indicates whether the user is enabled or disabled.
     * A disabled user cannot be authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    boolean isEnabled();

    /**
     * Indicates whether the user can expire.
     *
     * @return <code>true</code> if the user credentials never expire,
     *         <code>false</code> if can expire
     */
    boolean accountNeverExpire();

    /**
     * Indicates whether the user's account has expired.
     * An expired account cannot be authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
     *         (ie expired)
     */
    boolean isAccountNonExpired();

//    /**
//     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
//     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
//     */
//    boolean isAccountNonLocked();

    /**
     * Indicates whether the user's credentials (password) has to be changed at first.
     *
     * @return <code>true</code> if the user's credentials has to be changed at first <code>false</code> if no needed
     *         to be changed at first
     */
    boolean credentialsHasToBeChangedAtFirst();


    /**
     * Indicates whether the user's credentials (password) can expire.
     *
     * @return <code>true</code> if the user's credentials never expire,
     *         <code>false</code> if can expire
     */
    boolean credentialsNeverExpire();

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent
     * authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     *         <code>false</code> if no longer valid (ie expired)
     */
    boolean isCredentialsNonExpired();
}
