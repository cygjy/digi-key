package com.jd.resource.service.impl;

import com.jd.resource.service.RunnableWithTime;


/**
 * @author chenyang
 */
public class MyRunnable implements RunnableWithTime {
    /**
     * 系统时间1
     */
    private long time1;

    /**
     * 系统时间2
     */
    private long time2;

    @Override
    public long getTime1() {
        return time1;
    }

    @Override
    public long getTime2() {
        return time2;
    }

    @Override
    public void run() {
        //
        time1 = System.currentTimeMillis();
        System.out.println("getTime1------------->:"+time1);
        time2 = System.currentTimeMillis()+1000;
        System.out.println("getTime2------------->:"+time2);
    }


}
