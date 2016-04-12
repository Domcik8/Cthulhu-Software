/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.vu.mif.labanoro_draugai.authentication.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author AlaNote
 */
public class RegisterForm {
    @NotNull
    @Size(min=3)
    public String userName;
    @NotNull
    @Size(min=3)
    public String password;
    @Size(min=3)
    @NotNull
    public String confirmPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
