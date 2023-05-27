package com.yeongjin.YeongJin;

import com.yeongjin.YeongJin.Config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class YeongJinApplication {
	public static void main(String[] args) {
		SpringApplication.run(YeongJinApplication.class, args);
	}

}
