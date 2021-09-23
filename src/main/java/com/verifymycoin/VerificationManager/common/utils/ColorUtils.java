package com.verifymycoin.VerificationManager.common.utils;

import lombok.experimental.UtilityClass;

import java.awt.*;

@UtilityClass
public class ColorUtils {
    public Color getColor(String textColor) {
        if (textColor == null || textColor.length() != 7) {
            throw new RuntimeException("배경색을 입력해 주세요.");
        }
        return new Color(
                Integer.valueOf(textColor.substring(1, 3), 16),
                Integer.valueOf(textColor.substring(3, 5), 16),
                Integer.valueOf(textColor.substring(5, 7), 16)
        );
    }
}