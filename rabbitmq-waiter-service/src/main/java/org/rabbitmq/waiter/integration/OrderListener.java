package org.rabbitmq.waiter.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {



    @StreamListener(Barista.FINISHED_ORDERS)
    public void listenFinishOrder(String id){
        log.info("finishOrer[{}]",id);
    }
}
