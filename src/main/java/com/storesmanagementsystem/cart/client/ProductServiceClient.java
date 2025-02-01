package com.storesmanagementsystem.cart.client;

import com.storesmanagementsystem.cart.contracts.CommonResponse;
import com.storesmanagementsystem.cart.contracts.DealerProductInfoBean;
import com.storesmanagementsystem.cart.contracts.ProductInfoBean;
import com.storesmanagementsystem.cart.contracts.UserInfoBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "productServiceClient",url = "http://localhost:8082/Product", configuration = ProductServiceClientConfig.class)
public interface ProductServiceClient {

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getProduct(@PathVariable("id") int productId);

    @PutMapping( consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateProduct(@RequestParam("transactionType") String transactionType, ProductInfoBean bean);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String addProduct(UserInfoBean bean);

    @GetMapping(value = "/Dealer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public CommonResponse getDealerProduct(@PathVariable("id") int productId);

    @PutMapping( value = "/Dealer", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateDealerProduct(@RequestParam("transactionType") String transactionType, DealerProductInfoBean bean);
}
