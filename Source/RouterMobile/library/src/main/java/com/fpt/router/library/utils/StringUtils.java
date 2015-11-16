package com.fpt.router.library.utils;

import android.util.Log;

import com.fpt.router.library.utils.string.AccentRemoval;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Huynh Quang Thao on 10/19/15.
 */
public class StringUtils {
    public static String normalizeString(String s) {
        s = s.toLowerCase().trim().replaceAll("\\s+", " ");
        // s = s.replaceAll("\\.", "");
        s = AccentRemoval.removeAccent(s);
        return s;
    }

    public static String normalizeFileCache(String s) {
        s = s.toLowerCase().trim().replaceAll("\\s+", " ");
        // s = s.replaceAll("\\.", "");
        s = AccentRemoval.removeAccent(s);
        s = s.replaceAll(" ", "_");
        s = s.replaceAll("[^a-z0-9_-]", "");
        return s;
    }

    public static byte[] convertToByteArray(String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToString(byte[] encode) {
        try {
            return new String(encode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] convertInputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toByteArray();
    }

    public static Asset convertStringToAsset(String s) {
        byte[] encode = convertToByteArray(s);
        return Asset.createFromBytes(encode);
    }

    public static String convertAssetToString(GoogleApiClient googleApiClient, Asset asset) {
        ConnectionResult result =
                googleApiClient.blockingConnect(6000, TimeUnit.MILLISECONDS);
        if (!result.isSuccess()) {
            Log.e("hqthao", "Sending asset. timeout when reconnecting :|");
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(
                googleApiClient, asset).await().getInputStream();

        // googleApiClient.disconnect();

        if (assetInputStream == null) {
            Log.e("hqthao", "Requested an unknown Asset.");
            return null;
        }

        byte[] data = convertInputStreamToByteArray(assetInputStream);

        return convertToString(data);
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

    public static String removeCharacter(String s){
        if((s.equals(""))||(s.length()==0)){
            return s;
        }
        if(s.endsWith(", Việt Nam")){
            s = s.substring(0,s.length() -10);
            if(s.endsWith("Hồ Chí Minh")){
                s = s.replace("Hồ Chí Minh","Tp.HCM;");
            }

        }
        if(s.endsWith(", Việt Nam;")){
            s = s.substring(0,s.length()-11);
            if(s.endsWith("Hồ Chí Minh")){
                s = s.replace("Hồ Chí Minh","Tp.HCM;");
            }
        }
        return s;
    }

    public  static boolean equalLists(List<String> one, List<String> two){
        if (one == null && two == null){
            return true;
        }

        if((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()){
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        //as noted in comments by A. R. S.
        one = new ArrayList<String>(one);
        two = new ArrayList<String>(two);

        //Collections.sort(one);
        //Collections.sort(two);
        return one.equals(two);
    }
}
