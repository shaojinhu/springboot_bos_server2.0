package com.bos.util;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("all")
public class ApiUtils {

    /**
     * 发送短信
     * @param phone 手机号码
     * @param number 验证码
     * @return
     */
    private static String testSend(String phone,String number){
        // just replace key here
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
            "api","bd43025e01da1045b2122c4ac7e61440"));
        WebResource webResource = client.resource(
            "http://sms-api.luosimao.com/v1/send.json");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("mobile", phone);
        formData.add("message", "您的验证码："+number+"，请在60秒内尽快验证,折磨王【铁壳测试】");
        ClientResponse response =  webResource.type( MediaType.APPLICATION_FORM_URLENCODED ).
        post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        System.out.print(textEntity);
        System.out.print(status);
        return textEntity;
    }

    /**
     * 查看余额
     * @return
     */
    private static String testStatus(){
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
            "api","bd43025e01da1045b2122c4ac7e61440"));
        WebResource webResource = client.resource( "http://sms-api.luosimao.com/v1/status.json" );
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        ClientResponse response =  webResource.get( ClientResponse.class );
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        System.out.print(status);
        System.out.print(textEntity);
        return textEntity;
    }

    public static void main(String[] args) {
        //testSend("19832113007","略略略");
        testStatus();
    }
}