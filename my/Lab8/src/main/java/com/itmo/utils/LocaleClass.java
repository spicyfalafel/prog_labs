package com.itmo.utils;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleClass {
    @Getter
    public ResourceBundle resourceBundle;

    public LocaleClass(){
         resourceBundle = ResourceBundle
                .getBundle("locals", Locale.forLanguageTag("RU"), new UTF8Control());
    }

    public LocaleClass(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void changeLocale(Locale locale){
        resourceBundle = ResourceBundle
                .getBundle("locals", locale, new UTF8Control());
    }

    public enum SupportedLanguages{
        RU,
        EST,
        SPA,
        SWE
    }


    public void changeLocaleByTag(String TAG){
        changeLocale(Locale.forLanguageTag(TAG));
    }
    public void changeLocaleByTag(SupportedLanguages TAG){
        changeLocale(Locale.forLanguageTag(TAG.toString()));
    }

    public String getString(String text){
        return resourceBundle.getString(text);
    }
}
