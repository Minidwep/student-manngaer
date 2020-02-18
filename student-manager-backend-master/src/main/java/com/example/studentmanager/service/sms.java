package com.example.studentmanager.service;


import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

import com.tencentcloudapi.sms.v20190711.SmsClient;

import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

    public class sms
    {
        public static void main(String [] args) {
            try{

                Credential cred = new Credential("AKIDreJpHe4AMQK9uaFdbQ7iAbvcGqHcDoiX", "qZ9e0FLH8saEk6IKKG4viTMAsRq6CLOJ");

                HttpProfile httpProfile = new HttpProfile();
                httpProfile.setEndpoint("sms.tencentcloudapi.com");

                ClientProfile clientProfile = new ClientProfile();
                clientProfile.setHttpProfile(httpProfile);

                SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

                String params = "{\"PhoneNumberSet\":[\"+8615628931723\"],\"TemplateID\":\"494663\",\"Sign\":\"51chen师生管理系统\",\"TemplateParamSet\":[\"11111wdnmd\"],\"SmsSdkAppid\":\"1400293928\"}";
                SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);

                SendSmsResponse resp = client.SendSms(req);

                System.out.println(SendSmsRequest.toJsonString(resp));
            } catch (TencentCloudSDKException e) {
                System.out.println(e.toString());
            }

        }


}
