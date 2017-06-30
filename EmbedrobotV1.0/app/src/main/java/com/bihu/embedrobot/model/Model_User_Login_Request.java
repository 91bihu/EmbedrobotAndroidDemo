package com.bihu.embedrobot.model;

import java.io.Serializable;

/**
 * 请求登录对象
 * Created by houxianjun on 16/7/29.
 */
public class Model_User_Login_Request implements Serializable {
    //从壁虎平台获取的经纪人id
    int AgentId;
    //合作方内部系统登录的用户名
    String UserName;
    //请求接口当前时间戳
    String Timestamp;

    //到期时间戳
    String ExpireTime;
    String SecCode;
    //APP获取手机的机器码
    String UniqueCode;
    public int getAgentId() {
        return AgentId;
    }

    public void setAgentId(int agentId) {
        this.AgentId = agentId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.Timestamp = timestamp;
    }



    public String getExpireTime() {
        return ExpireTime;
    }

    public void setExpireTime(String expireTime) {
        this.ExpireTime = expireTime;
    }

    public String getSecCode() {
        return SecCode;
    }

    public void setSecCode(String secCode) {
        this.SecCode = secCode;
    }

    public String getUniqueCode() {
        return UniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        UniqueCode = uniqueCode;
    }
}
