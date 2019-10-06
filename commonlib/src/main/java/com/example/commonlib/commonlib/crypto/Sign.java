package com.example.commonlib.commonlib.crypto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class Sign {


    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_SUPPORT = "support";
    //签名本地缓存
    private static Map<String, JWTVerifier> verifierMap = new HashMap<>();

    /**
     *
     * @param tokenString token
     * @param singString  签名
     * @return
     */
    public static DecodedJWT verifierToken(String tokenString,String singString){
        JWTVerifier jwtVerifier = verifierMap.get(singString);
        if(jwtVerifier == null){
            synchronized (verifierMap){
                if(jwtVerifier == null){
                    //加载标签
                    Algorithm algorithm = Algorithm.HMAC512(singString);
                    jwtVerifier = JWT.require(algorithm).build();
                    verifierMap.put(singString,jwtVerifier);
                }
            }
        }
        DecodedJWT jwt = jwtVerifier.verify(tokenString);
        return jwt;
    }
}
