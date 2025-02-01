package com.storesmanagementsystem.cart.client;

import com.storesmanagementsystem.cart.contracts.CommonResponse;
import com.storesmanagementsystem.cart.contracts.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "orderServiceClient",url = "http://localhost:8083/Order", configuration = OrderServiceClientConfig.class)
public interface OrderServiceClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse placeOrder(OrderInfo orderInfo);
}
