package com.codetratise;

import com.codetreatise.util.HttpClient;
import com.codetreatise.util.SerialUtils.ParamConfig;
import com.codetreatise.util.SerialUtils.SerialPortUtils;
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

    /**
     * 八、串口读取测试
     *
     * 参考文章 https://www.cnblogs.com/new-life/p/9345849.html
     *
     * @throws Exception
     */
    @Test
    public void serialPortUtilsest() {

        try {

            // 实例化串口操作类对象
            SerialPortUtils serialPort = new SerialPortUtils();
            // 创建串口必要参数接收类并赋值，赋值串口号，波特率，校验位，数据位，停止位
            ParamConfig paramConfig = new ParamConfig("COM4", 9600, 0, 8, 1);
            // 初始化设置,打开串口，开始监听读取串口数据
            serialPort.init(paramConfig);
            // 调用串口操作类的sendComm方法发送数据到串口
            serialPort.sendComm("FEF10A000000000000000AFF");
            // 关闭串口
            serialPort.closeSerialPort();
        } catch (Exception e) {

            System.out.println(e.fillInStackTrace());
        }


    }


}
