package com.wyu.plato.common;


import com.wyu.plato.common.model.LocalUser;

/**
 * @author novo
 * @date 2022-07-13 16:09
 */
public class LocalUserThreadHolder {
    private static final ThreadLocal<LocalUser> threadLocal = new ThreadLocal<>();

    public static void setLocalUser(LocalUser localUser) {
        LocalUserThreadHolder.threadLocal.set(localUser);
    }

    public static LocalUser getLocalUser() {
        return LocalUserThreadHolder.threadLocal.get();
    }

    public static Integer getLocalUserScope() {
        return LocalUserThreadHolder.threadLocal.get().getScope();
    }

    public static Long getLocalUserId() {
        return LocalUserThreadHolder.threadLocal.get().getId();
    }

    public static Long getLocalUserNo() {
        return LocalUserThreadHolder.threadLocal.get().getAccountNo();
    }

    public static void clear() {
        LocalUserThreadHolder.threadLocal.remove();
    }
}
