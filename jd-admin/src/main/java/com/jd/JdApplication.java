package com.jd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author jd
 */
@EnableScheduling
@EnableAsync
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class JdApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(JdApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  App启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}