package org.rabbitmq.barista.controller;

import lombok.extern.slf4j.Slf4j;
import org.rabbitmq.barista.support.OrderEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CustomerController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;


    @PostMapping("/payOrder")
    public String createAndPayOrder(@RequestParam String orderName){
        log.info("order is PAID: {}", orderName);

        eventPublisher.publishEvent(new OrderEvent(orderName));
        return "orderList";
    }




    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
