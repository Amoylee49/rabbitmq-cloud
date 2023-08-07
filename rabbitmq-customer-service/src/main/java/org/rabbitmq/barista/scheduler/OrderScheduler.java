package org.rabbitmq.barista.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.rabbitmq.barista.support.OrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OrderScheduler {

    private Map<String,Object> orderMap = new ConcurrentHashMap<>();

    @EventListener
    public void acceptOrder(OrderEvent event){
        orderMap.put(event.getEventAccept(),event.getEventAccept());
    }

    @Scheduled(fixedRate = 5_000)
    public void waitForCoffer() throws InterruptedException {
        if (orderMap.isEmpty())
            return;
        log.info("等待- wait for coffer");
        log.info("coffee 状态改为 takennnnnn");

        TimeUnit.SECONDS.sleep(5);
        if (orderMap.size()>3)
            orderMap.remove(1);
    }


















}
