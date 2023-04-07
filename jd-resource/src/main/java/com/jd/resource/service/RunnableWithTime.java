package com.jd.resource.service;


/**
 * 时间比较
 */
public interface RunnableWithTime extends Runnable {

    /**
     * 获取系统时间
     * @return
     */
    long getTime1();


    long getTime2();
}
