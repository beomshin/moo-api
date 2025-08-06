package com.kr.moo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SHA265Util {

    // SHA-256 해시 암호화 메서드
    public static String getEncryptText (String text, byte[] salt) {
        String encrptText = null;

        try {
            // sha-256 알고리즘 객체 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // salt 적용 (무작위 바이트 데이터 추가 보안 강화하기 위해)
            md.update(salt);

            // 원본 텍스트를 byte[] 형태로 해시 처리
            byte[] bytes = md.digest(text.getBytes());

            // 결과 byte[]을 16진수 문자열로 변환
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            encrptText = sb.toString();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return encrptText;
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
