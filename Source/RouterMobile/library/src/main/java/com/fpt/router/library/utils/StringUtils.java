package com.fpt.router.library.utils;

import android.util.Log;

import com.fpt.router.library.utils.string.AccentRemoval;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    public static Pattern firstPattern = Pattern.compile("(.*\\b)từ(\\b.*)");
    public static Pattern secondPattern = Pattern.compile("(.*\\b)đến(\\b.*)");
    public static Pattern thirdPattern = Pattern.compile("(.*\\b)qua(\\b.*)");;
    public static Pattern fourthPattern = Pattern.compile("(.*\\b)tới(\\b.*)");

    public static Map<Integer, String> processSpeech(String speech) {
        Map<Integer, String> tokens = new HashMap<>();

        if (speech.contains("tôi muốn đi")) {
            speech = speech.replace("tôi muốn đi", "");
            Log.e("hqthao", "new string 1: " + speech);
        }

        /*if (speech.contains("cho tôi đến")) {
            speech = speech.replace("cho tôi", "");
            Log.e("hqthao", "new string 2: " + speech);
        }*/

        speech = speech.trim();

        // context 1. diem trung gian thu nhat la A
        if (speech.contains("điểm trung gian thứ nhất là")) {
            speech = speech.replace("điểm trung gian thứ nhất là", "").trim();
            tokens.put(3, speech);
            return tokens;
        }

        // context 2. diem trung gian thu hai la B
        if (speech.contains("điểm trung gian thứ hai là")) {
            speech = speech.replace("điểm trung gian thứ hai là", "").trim();
            tokens.put(4, speech);
            return tokens;
        }

        // context 3. diem khoi hanh
        if (speech.contains("điểm xuất phát là")) {
            speech = speech.replace("điểm xuất phát là", "").trim();
            tokens.put(1, speech);
            return tokens;
        }

        // context 4. diem ket thuc
        if (speech.contains("điểm kết thúc là")) {
            speech = speech.replace("điểm kết thúc là", "").trim();
            tokens.put(2, speech);
            return tokens;
        }

        // assume sentence only has one tu / den / qua / toi
        // context 5. (toi muon di) tu A den B qua C toi D
        boolean isFound = false;
        String token;

        Matcher matcher = fourthPattern.matcher(speech);
        if (matcher.matches()) {
            isFound = true;
            token = matcher.group(2).trim();
            if (token.length() > 0) tokens.put(4, token);
            speech = matcher.group(1);
        }

        matcher = thirdPattern.matcher(speech);
        if (matcher.matches()) {
            isFound = true;
            token = matcher.group(2).trim();
            if (token.length() > 0) tokens.put(3, token);
            speech = matcher.group(1);
        }

        matcher = secondPattern.matcher(speech);
        if (matcher.matches()) {
            isFound = true;
            token = matcher.group(2).trim();
            Log.e("hqthao", "group 2 string 2: " + token);
            if (token.length() > 0) tokens.put(2, token);
            speech = matcher.group(1);
            Log.e("hqthao", "group 1 string 2: " + speech);
        }

        matcher = firstPattern.matcher(speech);
        if (matcher.matches()) {
            isFound = true;
            token = matcher.group(2).trim();
            Log.e("hqthao", "group 2 string 1: " + token);
            if (token.length() > 0) tokens.put(1, token);
        }

        if (isFound) {
            return tokens;
        }

        return tokens;
    }

}
