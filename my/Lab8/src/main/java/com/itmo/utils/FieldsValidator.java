package com.itmo.utils;

import com.itmo.collection.DragonWithStringFields;
import lombok.Getter;

import java.sql.Date;
import java.util.Arrays;

public class FieldsValidator {

    @Getter
    private final boolean mainFieldsOK;
    @Getter
    private final boolean killerFieldsEmpty;
    @Getter
    private final boolean killerFieldsRequredNotEmpty;
    public FieldsValidator(DragonWithStringFields d) {
        mainFieldsOK = dragonHasMainFieldsInitializated(d);
        killerFieldsEmpty = dragonKillerFieldsAreAllEmpty(d);
        killerFieldsRequredNotEmpty = dragonKillerRequaredFieldsAreNotEmpty(d);
    }

    public boolean isPositive(Number n) {
        return (n.intValue() > 0);
    }

    public boolean isPositive(String n) {
        if (isInt(n)) {
            return isPositive(Integer.parseInt(n));
        } else if (isLong(n)) {
            return isPositive(Long.parseLong(n));
        } else if (isFloat(n)) {
            return isPositive(Float.parseFloat(n));
        }
        return false;
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDate(String date) {
        try {
            Date.valueOf(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean stringsAreNullOrEmpty(String... str) {
        return Arrays.stream(str).allMatch(this::stringIsNullOrEmpty);
    }

    public boolean stringsAreNotNullOrEmpty(String... strs) {
        return Arrays.stream(strs).noneMatch(this::stringIsNullOrEmpty);
    }

    public boolean stringIsNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }


    public boolean stringIsNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    // killer = null only if every field of killer and location is empty
    public boolean dragonFieldsAreAllFine(DragonWithStringFields d) {
        return dragonHasMainFieldsInitializated(d) &&
                (dragonKillerFieldsAreAllEmpty(d) || dragonKillerRequaredFieldsAreNotEmpty(d));
    }

    public boolean dragonHasMainFieldsInitializated(DragonWithStringFields d) {
        return stringIsNotNullOrEmpty(d.getName()) &&
                isInt(d.getX()) &&
                isLong(d.getY()) &&
                isPositive(d.getWingspan()) &&
                isInt(d.getAge()) &&
                isPositive(d.getAge());
    }

    public boolean dragonKillerFieldsAreAllEmpty(DragonWithStringFields d) {
        return
                stringsAreNullOrEmpty(
                    d.getKillerName(),
                    d.getBirthday(),
                    d.getLocName(),
                    d.getKillerX(),
                    d.getKillerY(),
                    d.getKilllerZ()
                ) &&
                d.getNationality() == null &&
                d.getHairColor() == null;
    }

    public boolean dragonKillerRequaredFieldsAreNotEmpty(DragonWithStringFields d){
        boolean someStringsAreNotEmpty = stringsAreNotNullOrEmpty(d.getName(),
                d.getKillerName(),
                d.getLocName(),
                d.getKillerX(),
                d.getKillerY(),
                d.getKilllerZ());
        boolean dateIsDate =  isDate(d.getBirthday());
        return someStringsAreNotEmpty && dateIsDate &&
                        isInt(d.getKillerX()) && isLong(d.getKillerY()) && isFloat(d.getKilllerZ());
    }
}
