package com.app.aljazierah.object.request;

/**
 * Created by Mustanser Iqbal on 12/12/2017.
 */

public class ForgetPass {

    String email, forget_pass, lang, verify_code, new_pass;

    public ForgetPass(String email, String forget_pass, String lang) {
        this.email = email;
        this.forget_pass = forget_pass;
        this.lang = lang;
    }
    public ForgetPass(String email, String verify_code, String lang, int i) {
        this.email = email;
        this.verify_code = verify_code;
        this.lang = lang;
    }
    public ForgetPass(String email, String verify_code, String lang, int i, String newPass) {
        this.email = email;
        this.verify_code = verify_code;
        this.lang = lang;
        this.new_pass = newPass;
    }
}
