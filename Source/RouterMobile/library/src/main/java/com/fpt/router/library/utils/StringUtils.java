package com.fpt.router.library.utils;

import android.util.Log;

import com.fpt.router.library.utils.string.AccentRemoval;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Huynh Quang Thao on 10/19/15.
 */
public class StringUtils {
    public static String normalizeString(String s) {
        s = s.toLowerCase().trim().replaceAll("\\s+", " ");
        s = s.replaceAll("\\.", "");
        s = AccentRemoval.removeAccent(s);
        return s;
    }

    public static Map<Integer, String> processSpeech(String speech) {
        Map<Integer, String> tokens = new HashMap<>();

        if (speech.contains("tôi muốn đi")) {
            speech = speech.replace("tôi muốn đi", "");
            Log.e("hqthao", "new string 1: " + speech);
        }

        if (speech.contains("cho tôi đến")) {
            speech = speech.replace("cho tôi", "");
            Log.e("hqthao", "new string 2: " + speech);
        }

        speech = speech.trim();

        // assume sentence only has one tu / den / qua / toi
        // context 1. (toi muon di) tu A den B qua C toi D
        // context 2. (toi muon di) tu A
        // context 3. (toi muon di) den B
        String[] tokenizers = speech.split("\\\\s+");
        int firstIndex = speech.indexOf("từ");
        int secondIndex = speech.indexOf("đến");
        int thirdIndex = speech.indexOf("qua");
        int fourthIndex = speech.indexOf("tới");

        boolean isFound = false;
        String token;

        if (fourthIndex != -1) {
            isFound = true;
            token = speech.substring(fourthIndex + 3).trim();
            Log.e("hqthao", "token 4: " + token);
            if (token.length() > 0) tokens.put(4, token);
            speech = speech.substring(0, fourthIndex);
        }

        if (thirdIndex != -1) {
            isFound = true;
            token = speech.substring(thirdIndex + 3).trim();
            Log.e("hqthao", "token 3: " + token);
            if (token.length() > 0) tokens.put(3, token);
            speech = speech.substring(0, thirdIndex);
        }

        if (secondIndex != -1) {
            isFound = true;
            token = speech.substring(secondIndex + 3).trim();
            Log.e("hqthao", "token 2: " + token);
            if (token.length() > 0) tokens.put(2, token);
            speech = speech.substring(0, secondIndex);
        }

        if (firstIndex != -1) {
            isFound = true;
            token = speech.substring(firstIndex + 2).trim();
            Log.e("hqthao", "token 1: " + token);
            if (token.length() > 0) tokens.put(1, token);
        }

        if (isFound) {
            return tokens;
        }


        // context 4. diem trung gian thu nhat la A
        if (speech.contains("điểm trung gian thứ nhất là")) {
            speech = speech.replace("điểm trung gian thứ nhất là", "").trim();
            tokens.put(3, speech);
            return tokens;
        }

        // context 5. diem trung gian thu hai la B
        if (speech.contains("điểm trung gian thứ hai là")) {
            speech = speech.replace("điểm trung gian thứ hai là", "").trim();
            tokens.put(4, speech);
            return tokens;
        }

        return tokens;
    }

}
