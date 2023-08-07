package org.rabbitmq.barista.support;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Builder
@Data
public class OrderEvent extends ApplicationEvent {

    private String eventAccept = "";

    public OrderEvent(Object order) {
        super(order);
        this.eventAccept = (String) order;
    }


}
