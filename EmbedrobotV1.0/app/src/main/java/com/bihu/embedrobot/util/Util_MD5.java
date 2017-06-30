package com.bihu.embedrobot.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by houxianjun on 16/7/29.
 */
public class Util_MD5 {


    /**
     * 排序方法
     * @param hm
     * @return
     */
    public static String[] sortStringArray(HashMap<String,String> hm) {

        String [] arrayToSort = new String[hm.size()];

        ArrayList<String> arrayList= new ArrayList<String>();

        for(Map.Entry<String, String> entry:hm.entrySet()){

            System.out.println(entry.getKey()+"--->"+entry.getValue());

            String content = entry.getKey()+"="+entry.getValue()+"&";

            arrayList.add(content);
        }

        for (int i=0;i<arrayList.size();i++){
            arrayToSort[i]=arrayList.get(i);
        }

        System.out.println("字符型数组排序,排序前:");
        for (int i = 0; i < arrayToSort.length; i++)
        { System.out.print(arrayToSort[i]+",");
        }

        System.out.println(); System.out.println("排序后:");
        // 调用数组的静态排序方法sort,且不区分大小写
        Arrays.sort(arrayToSort,String.CASE_INSENSITIVE_ORDER);

        for (int i = 0; i < arrayToSort.length; i++)
        {
            System.out.print(arrayToSort[i]+",");
        }
        return arrayToSort;
    }

    /**
     *
     * @param plainText
     *            明文
     * @return 32位密文
     */
    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {


            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(plainText.getBytes()); // 得到加密后的字符组数

            StringBuffer sb = new StringBuffer();

            for (byte b : result) {
                int num = b & 0xff; // 这里的是为了将原本是byte型的数向上提升为int型，从而使得原本的负数转为了正数
                String hex = Integer.toHexString(num); //这里将int型的数直接转换成16进制表示
                //16进制可能是为1的长度，这种情况下，需要在前面补0，
                if (hex.length() == 1) {
                    sb.append(0);
                }
                sb.append(hex);
            }

            return sb.toString();



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    public static String encryption(String [] tmpplainText){


        StringBuffer plainText=new StringBuffer();

        for(int i = 0;i<tmpplainText.length;i++){
            plainText.append(tmpplainText[i]);
        }


        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            Log.d("md5",plainText.toString().substring(0,plainText.length()-1));
            md.update((plainText.toString().substring(0,plainText.length()-1)).getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Log.d("md5","加密后的串:"+re_md5);


        return re_md5;
    }

}
