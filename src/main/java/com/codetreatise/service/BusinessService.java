package com.codetreatise.service;

import java.util.List;
import java.util.Map;

public interface BusinessService {

    /**
     * 用户身份验证接口
     *
     * @param email
     * @param password
     * @return
     */
    Map authenticate(String email, String password);

    /**
     * 任务列表
     *
     * @param token
     * @param date
     * @return
     */
    Map getTaskList(String token, String date);


    /**
     * 获取具体任务信息
     *
     * @param token
     * @param taskId
     * @return
     */
    Map getTaskItem(String token, Integer taskId);

    /**
     * 锁定该任务
     *
     * @param token
     * @param taskId
     * @return
     */
    Map doTaskReceive(String token, Integer taskId);

    /**
     * 已领取任务接口
     *
     * @param token
     * @param date
     * @return
     */
    Map getTaskOrder(String token, String date);

    /**
     * 批次号选择
     *
     * @param token
     * @param taskId
     * @return
     */
    Map getTaskBatch(String token, Integer taskId);

    /**
     * 填报分拣任务
     * @param token
     * @param taskId
     * @param data
     * @return
     */
    Map doFillOrder(String token, Integer taskId, List<Map<String, String>> data);


}
