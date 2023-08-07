package org.rabbitmq.barista.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OrderListener {

    @Autowired
    @Qualifier(Waiter.FINISHED_ORDERS)
    private MessageChannel messageChannel;
    @Value("${order.barista-prefix}-random.uuid")
    private String barista;


    @StreamListener(value = Waiter.NEW_ORDERS)
    @SendTo(Waiter.FINISHED_ORDERS)
    public void processNewOrder(List<String> id) throws InterruptedException {
        log.info("paid id is{}", id);
        id.add("finished");
        id.add(barista);

        TimeUnit.SECONDS.sleep(7);

        messageChannel.send(MessageBuilder.withPayload(id).build());
    }
}
