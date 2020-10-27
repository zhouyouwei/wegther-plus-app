package com.codetreatise.service.impl;

import com.codetreatise.service.BusinessService;
import com.codetreatise.util.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements BusinessService {


    @Value("${server.url}")
    private String baseUrl;


    @Override
    public Map authenticate(String username, String password) {

        String loginUrl = baseUrl.concat("openapi/sorting/?op=login");

        Map<String, Object> paras = new HashMap<>();
        paras.put("username", username);// "18805320011"
        paras.put("password", password);//"123456"

        Map respones = HttpClient.doPost(loginUrl, paras);

        System.out.println("authenticate result:");
        System.out.println(respones);

        return respones;
    }


    @Override
    public Map getTaskList(String token, String date) {

        String taskListUrl = baseUrl.concat("openapi/sorting/?op=task");

        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", token);
        paras.put("day", date);//分拣日期，可选，默认当天的 "2020-10-14"

        Map respones = HttpClient.doPost(taskListUrl, paras);

        System.out.println("op=task result:");
        System.out.println(respones);

        return respones;
    }


    @Override
    public Map getTaskItem(String token, Integer taskId) {

        String taskItemUrl = baseUrl.concat("openapi/sorting/?op=task_item");

        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", token);
        paras.put("id", taskId);//任务ID

        Map respones = HttpClient.doPost(taskItemUrl, paras);

        System.out.println("op=task_item result:");
        System.out.println(respones);

        return respones;
    }

    @Override
    public Map doTaskReceive(String token, Integer taskId) {

        String receiveUrl = baseUrl.concat("openapi/sorting/?op=receive");
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", token);
        paras.put("id", taskId);//任务项目ID
        Map respones = HttpClient.doPost(receiveUrl, paras);

        System.out.println("op=receive result:");
        System.out.println(respones);

        return respones;
    }

    @Override
    public Map getTaskOrder(String token, String date) {
        String orderUrl = baseUrl.concat("openapi/sorting/?op=order");
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", token);
        paras.put("day", date);//任务日期 "2020-10-14"

        Map respones = HttpClient.doPost(orderUrl, paras);

        System.out.println("op=order result:");
        System.out.println(respones);

        return respones;
    }

    @Override
    public Map getTaskBatch(String token, Integer taskId) {
        String batchUrl = baseUrl.concat("openapi/sorting/?op=batch");
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", token);
        paras.put("id", taskId);

        Map respones = HttpClient.doPost(batchUrl, paras);

        System.out.println("op=batch result:");
        System.out.println(respones);

        return respones;
    }

    @Override
    public Map doFillOrder(String token, Integer taskId, List<Map<String, String>> data) {

        String fillOrderUrl = baseUrl.concat("openapi/sorting/?op=fill");
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", token);
        paras.put("id", 1);//订单商品项目ID
        paras.put("data", data);//序列化填报数数量

        Map respones = HttpClient.doPost(fillOrderUrl, paras);
        System.out.println("op=fill result:");
        System.out.println(respones);

        return respones;
    }
}
