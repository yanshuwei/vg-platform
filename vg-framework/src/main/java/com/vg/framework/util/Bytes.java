package com.vg.framework.util;

/**
 * @author sqp
 * @date 2018/10/15 9:05
 */
public class Bytes {
    public static String substring(String src, int start_idx, int end_idx){
        byte[] b = src.getBytes();
        String tgt = "";
        for(int i=start_idx; i<=end_idx; i++){
            tgt +=(char)b[i];
        }
        return tgt;
    }
}
