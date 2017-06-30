/*
 * Copyright (c) 2013 The AroundLearnBar Project,
 *
 * 清大教育集团. All Rights Reserved.
 */

package com.bihu.embedrobot.https;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Function: MD5
 * @Date: 2013-10-23 下午3:30:05
 * @author LuoYong
 * @version
 */
public class MD5 {
	
	private static String encode(String str, String method) {

		MessageDigest md = null;
		String dstr = null;
		try {
			md = MessageDigest.getInstance(method);
			md.update(str.getBytes());
			dstr = new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dstr;
	}
	
	//public static String encodeMD5String(String str){
//		return encode(str, "MD5");
//	}
	
	public static String Md5(String str) {
		if (str != null && !str.equals("")) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
				byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < md5Byte.length; i++) {
					sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
					sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
				}
				str = sb.toString();
			} catch (NoSuchAlgorithmException e) {
			} catch (Exception e) {
			}
		}
		return str;
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
//			MessageDigest md = MessageDigest.getInstance("MD5");
//			md.update(plainText.getBytes());
//			byte b[] = md.digest();
//
//			int i;
//
//			StringBuffer buf = new StringBuffer("");
//			for (int offset = 0; offset < b.length; offset++) {
//				i = b[offset];
//				if (i < 0)
//					i += 256;
//				if (i < 16)
//					buf.append("0");
//				buf.append(Integer.toHexString(i));
//			}
//
//			re_md5 = buf.toString();

			// MessageDigest专门用于加密的类

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

	