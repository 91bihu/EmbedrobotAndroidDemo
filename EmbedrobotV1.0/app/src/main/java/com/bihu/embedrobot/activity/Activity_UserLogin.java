package com.bihu.embedrobot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.bihu.embedrobot.R;
import com.bihu.embedrobot.https.HttpUtils;

import com.bihu.embedrobot.model.Model_User_Login_Request;
import com.bihu.embedrobot.model.Model_User_Login_Response;
import com.bihu.embedrobot.util.SharedPerUtil;
import com.bihu.embedrobot.util.Util_App;
import com.bihu.embedrobot.util.Util_MD5;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by houxianjun on 16/7/19.
 */
public class Activity_UserLogin extends Activity {


    private Button loginBtn = null;
    private EditText accountInputBox = null;
    private EditText passwordInputBox = null;

    Model_User_Login_Response response = null;
    long expiretamp = System.currentTimeMillis() / 1000;
    String token = "";
    String SecretKey = "密钥";//顶级账号的密钥

//联合登录地址
    String url = "http://wx.91bihu.com/api/unite/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_userlogin);

        InitView();

        setListener();

    }

    public void InitView() {

        loginBtn = (Button) findViewById(R.id.login_btn);
        accountInputBox = (EditText) findViewById(R.id.account_input_view);
        passwordInputBox = (EditText) findViewById(R.id.password_input_view);

    }

    MyTask mTask;

    public void setListener() {


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTask = new MyTask(accountInputBox.getText().toString());
                mTask.execute();
            }
        });

    }


    /**
     * 处理消息
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            switch (msg.what) {

                //授权成功，页面跳转
                case 1:

                    //本地保存token
                    SharedPerUtil.getInstanse(Activity_UserLogin.this).setUserLoginState(token);

                    //保存此次成功登录后的token过期时间
                    SharedPerUtil.getInstanse(Activity_UserLogin.this).setUserLoginETIME(expiretamp+"");

                    //跳转至web页
                    Intent intent = new Intent(Activity_UserLogin.this, Activity_WebView.class);

                    //token
                    intent.putExtra("token", token);

                    Activity_UserLogin.this.startActivity(intent);

                    Activity_UserLogin.this.finish();
                    break;
            }


        }
    };


    /**
     * 异步请求登录数据
     */
    private class MyTask extends AsyncTask<String, Integer, String> {


        String username = "";
        public MyTask(String _username){

            this.username = _username;
        }

        @Override
        protected String doInBackground(String... strings) {


          String data =  sendLogin(username);

            return data;
        }




      @Override
      protected void onPostExecute(String result) {

          if (result==null){

           Toast.makeText(Activity_UserLogin.this, "请求失败", Toast.LENGTH_LONG).show();

           return;
           }

          if (!result.equals("") && !result.isEmpty()) {

              response = HttpUtils.getLoginObj(result);

              if (response != null) {

                  //登录成功
                  if (response.getCode().equals("200")) {

                      token = response.getToken();

                      Toast.makeText(Activity_UserLogin.this, "登录成功", Toast.LENGTH_LONG).show();


                      Message msg = new Message();
                      msg.what = 1;
                      handler.sendMessage(msg);

                  }

                  //未授权
                  else if (response.getCode().equals("401")) {

                      Toast.makeText(Activity_UserLogin.this, "未授权", Toast.LENGTH_LONG).show();
                  }

                  //验证不通过
                  else if (response.getCode().equals("406")) {
                      Toast.makeText(Activity_UserLogin.this, "验证不通过", Toast.LENGTH_LONG).show();

                  }
              } else {
                  Toast.makeText(Activity_UserLogin.this, "请求失败", Toast.LENGTH_LONG).show();

              }


          }


      }

     }


    /**
     * 发送登录请求
     *
     * @param _username 登录名称
     */
    public String sendLogin(String _username) {


        int agentId = 11111112;//顶级ID,根据提供的ID,自行替换

        String username = _username;

        //当前时间戳
        long timetamp = System.currentTimeMillis() / 1000;

        String path = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 1);

        date = calendar.getTime();

        // token时效 设置为当前登录时间延后一个月
        expiretamp = date.getTime() / 1000;

        System.out.println("增加月份后的日期：" + date.getTime());

        HashMap<String, String> hmSecCode = new HashMap<String, String>();

        //壁虎生成的AgentID
        hmSecCode.put("AgentId", agentId + "");
        //壁虎注册的用户名

            hmSecCode.put("UserName", _username);

        //当前时间戳
        hmSecCode.put("Timestamp", timetamp + "");
        //加密串 gsb23np5npnhk26
        hmSecCode.put("SecretKey", SecretKey);
        //token失效到期时间
        hmSecCode.put("ExpireTime", expiretamp + "");

        hmSecCode.put("UniqueCode", Util_App.getDeviceId(Activity_UserLogin.this)+"");

        //参数
        Model_User_Login_Request params = new Model_User_Login_Request();
        params.setAgentId(agentId);

            params.setUserName(_username);

        params.setTimestamp(timetamp + "");
        params.setExpireTime(expiretamp + "");

        //排序后,Md5加密
        params.setSecCode(Util_MD5.encryption(Util_MD5.sortStringArray(hmSecCode)));
        params.setUniqueCode(Util_App.getDeviceId(Activity_UserLogin.this)+"");
        //服务器请求路径
        String strUrlPath = url;

        //返回结果
        String strResult = HttpUtils.execute(strUrlPath, params);
        //String strResult = HttpUtils.notesubmit(strUrlPath, params);

        System.out.println(strResult);

        return strResult;


    }

    private static String changeCharSet(
            String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return str;
    }

    /**
     * 字符串转化为UTF-8
     * @param str
     * @return
     */
    public static String toUTF8(String str){
        String result = str;
        try {
            result = changeCharSet(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}