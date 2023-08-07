package org.rabbitmq.waiter.controller;

import cn.zhxu.bs.BeanSearcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.rabbitmq.waiter.integration.Barista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderPayController {
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private Barista barista;

    @Autowired
    private BeanSearcher beanSearcher;
    /**
     * http://www.manongjc.com/detail/64-ueudzyxxxzoqyvd.html //jsonNode
     * <p>
     * // 遍历jsonNode，responseMessage为初始jsonNode对象
     * Iterator<JsonNode> elements = responseMessage.path("data").path("items").elements();
     * List<String> ids = new ArrayList<>();
     * while (elements.hasNext()){
     * JsonNode node = elements.next();
     * String id = node.path("id").asText();
     * ids.add(id);
     * }
     *
     * @param id
     * @param json
     * @return
     */
    @PutMapping(value = "/pay/{id}")
    public boolean payOrders(@PathVariable String id, @RequestBody JsonNode json) throws IOException {
        beanSearcher.search(null);
        log.info("id-{},接收支付订单：{}", id, json);
        log.info("id-{},接收支付订单：{}", id, json.get("name").textValue());
        List<String> listStr = new ArrayList<>();
        listStr.add(id);
//        List.of(); 不可变list？
        if (json.isEmpty())
            return false;

        Stream.of(json).filter(s -> s.fieldNames().hasNext())
                .forEach(s -> {
                    s.fieldNames().forEachRemaining(key -> {
                        String value = s.get(key).textValue();
                        log.info("{}-{}", key, value);
                        listStr.add(value);
                    });
                });
        log.info("id is {}", id);

        barista.newOrders().send(MessageBuilder.withPayload(listStr).build());
        return true;
    }


    @SneakyThrows
    public static void main(String[] args) {
        var json = """ 
                {"status":"paid","name":"moka"} """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        HashMap<String, String> map = new HashMap<>();
        Stream.of(jsonNode).filter(s -> s.fieldNames().hasNext())
                .forEach(s -> {
                    s.fieldNames().forEachRemaining(key -> {
                        String value = s.get(key).textValue();
                        map.put(key, value);
                        log.info("{}-{}", key, value);
                    });
                });
        log.info("map集合：{}", map);
    }
}
