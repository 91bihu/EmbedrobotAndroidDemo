package com.bihu.embedrobot.https;

import com.bihu.embedrobot.model.Model_User_Login_Request;
import com.bihu.embedrobot.model.Model_User_Login_Response;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by houxianjun on 2017/6/26.
 */

public class HttpUtils {


    public  static String execute(String strUrlPath,Model_User_Login_Request dto) {

    InputStream inputStream = null;

        HttpURLConnection urlConnection = null;
       String data =  getRequestData(dto);
        try {

            URL url = new URL(strUrlPath);

            urlConnection = (HttpURLConnection) url.openConnection();

//            /* optional request header */
//            //设置请求体的类型是文本类型
//            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            //设置请求体的长度
//            urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length()));

     /* optional request header */

//            urlConnection.setRequestProperty("Accept-Charset", "utf-8");
     urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

           // urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length()));
        /* optional request header */
            urlConnection.setRequestProperty("Accept", "application/json");

            // read response
            /* for Get request */
            urlConnection.setRequestMethod("POST");

            urlConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

            //wr.writeBytes(getRequestData(dto));

            wr.write(getRequestData(dto).getBytes());

            wr.flush();

            wr.close();
            // try to get response

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200) {

                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                String response = dealResponseResult(inputStream);

                return response;


            }

            } catch(Exception e)

            {

             e.printStackTrace();

            }
            finally
             {
               if (inputStream != null) {
                  try {
                    inputStream.close();

                  } catch (IOException e) {

                      e.printStackTrace();

                    }

                   }

                  if (urlConnection != null) {
                       urlConnection.disconnect();
                   }
                  }
                return null;
             }



    /*
     * Function  :   封装请求体信息
     */
    public static String getRequestData(Model_User_Login_Request model) {
        String stringBuffer ="";        //存储封装好的请求体信息
        try {


            Gson gson = new Gson();
            stringBuffer= gson.toJson(model);


            System.out.println("json 串"+stringBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }


    /**
     * 字符串转对象
     * @param data
     * @return
     */
    public static Model_User_Login_Response getLoginObj(String data){

        Gson gson = new Gson();
        Model_User_Login_Response response = gson.fromJson(data, Model_User_Login_Response.class);//对于javabean直接给出class实例

        return response;

    }


    /*
     * Function  :   处理服务器的响应结果（将输入流转化成字符串）
     * Param     :   inputStream服务器的响应输入流
     */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }




}
