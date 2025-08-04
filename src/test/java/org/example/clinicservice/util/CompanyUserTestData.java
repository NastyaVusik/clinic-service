package org.example.clinicservice.util;

import org.example.clinicservice.domain.CompanyUser;

/**
 * Test data factory for CompanyUser objects.
 */
public class CompanyUserTestData {

    public static CompanyUser createDefault() {
        CompanyUser user = new CompanyUser();
        user.setId(1L);
        user.setLogin("test.user");
        return user;
    }

    public static CompanyUser createWithId(Long id) {
        CompanyUser user = createDefault();
        user.setId(id);
        return user;
    }

    public static CompanyUser createWithLogin(String login) {
        CompanyUser user = createDefault();
        user.setLogin(login);
        return user;
    }

    public static CompanyUser createSecondUser() {
        return createWithId(2L);
    }

    public static CompanyUser createNewUser() {
        CompanyUser user = new CompanyUser();
        user.setId(2L);
        user.setLogin("new.user");
        return user;
    }

    public static CompanyUser createLongLoginUser() {
        CompanyUser user = new CompanyUser();
        user.setId(3L);
        user.setLogin("very.long.login.name.that.should.still.work");
        return user;
    }

    public static CompanyUser createSpecialCharLoginUser() {
        CompanyUser user = new CompanyUser();
        user.setId(4L);
        user.setLogin("user@domain.com");
        return user;
    }
}
