package org.rabbitmq.barista;

import org.rabbitmq.barista.integration.Waiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(Waiter.class)
public class BaristaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaristaServiceApplication.class,args);
        System.out.println("测试branch");
    }

}