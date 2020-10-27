package com.codetratise;

import com.codetreatise.util.HttpClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessTest {


    static String access_token = "ZTUwYWIxNjcxMTM0MWE0YjBiYWU1ODc4OWJlYzY2Y2Y=";

    /**
     * 一、登录
     *
     * @throws Exception
     */
    @Test
    public void userLoginTest() {
        String url = "https://ps.shian360.com/openapi/sorting/?op=login";
        Map<String, Object> paras = new HashMap<>();
        paras.put("username", "18805320011");
        paras.put("password", "123456");
        Map result = HttpClient.doPost(url, paras);
        System.out.println("0    " + result);
    }

    /**
     * 二、分拣任务读取接口
     *
     * @throws Exception
     */
    @Test
    public void userGetTaskTest() {
        String url = "https://ps.shian360.com/openapi/sorting/?op=task";
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", access_token);
        paras.put("day", "2020-10-14");//分拣日期，可选，默认当天的
        Map result = HttpClient.doPost(url, paras);
        System.out.println("1    " + result);
    }


    /**
     * 三、分拣任务项目读取接口
     *
     * @throws Exception
     */
    @Test
    public void userGetTaskItemTest() {

        Integer taskNumberId = 1;
        String baseUrl = "https://ps.shian360.com/openapi/sorting/?";

        String taskItemUrl = baseUrl.concat("op=task_item");
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", access_token);
        paras.put("id", taskNumberId);//任务ID
        Map result = HttpClient.doPost(taskItemUrl, paras);

        System.out.println("2    " + result);
    }


    /**
     * 四、分拣任务项目领取接口
     *
     * @throws Exception
     */
    @Test
    public void userGetTaskReceiveTest() {

        String url = "https://ps.shian360.com/openapi/sorting/?op=receive";
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", access_token);
        paras.put("id", 1);//任务项目ID
        Map result = HttpClient.doPost(url, paras);
        System.out.println("3    " + result);
    }


    /**
     * 五、已领取任务接口
     *
     * @throws Exception
     */
    @Test
    public void userGetTaskOrderTest() {

        String url = "https://ps.shian360.com/openapi/sorting/?op=order";
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", access_token);
        paras.put("day", "2020-10-14");//任务日期
        Map result = HttpClient.doPost(url, paras);
        System.out.println("4    " + result);
    }


    /**
     * 六、批次号选择
     *
     * @throws Exception
     */
    @Test
    public void userGetTaskBatchTest() {

        String url = "https://ps.shian360.com/openapi/sorting/?op=batch";
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", access_token);
        paras.put("id", 1);
        Map result = HttpClient.doPost(url, paras);
        System.out.println("5    " + result);
    }


    /**
     * 七、商品分拣填报
     *
     * @throws Exception
     */
    @Test
    public void userFillTest() {

        String url = "https://ps.shian360.com/openapi/sorting/?op=fill";
        Map<String, Object> paras = new HashMap<>();
        paras.put("access_token", access_token);
        paras.put("id", 1);//订单商品项目ID

        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap();
        map.put("id", "14679");
        data.add(map);
        paras.put("data", data);//序列化填报数数量
        Map result = HttpClient.doPost(url, paras);
        System.out.println("6    " + result);
    }

}
