package com.storesmanagementsystem.cart.controller;

import com.storesmanagementsystem.cart.contracts.CartInfoBean;
import com.storesmanagementsystem.cart.contracts.CommonResponse;
import com.storesmanagementsystem.cart.contracts.OrderInfo;
import com.storesmanagementsystem.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CartServiceController {

    @Autowired
    CartService service;

    @PostMapping(path = "/Cart", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> addToCart(@RequestBody CartInfoBean bean) {
        CommonResponse resp = getRespStructure();
        if (service.addToCart(bean)) {
            resp.setStatus("SUCCESS");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.setStatus("FAILED");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @PostMapping(path = "/Cart/Checkout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> checkout(@RequestBody CartInfoBean bean) {
        OrderInfo checkout = service.checkout(bean);
        CommonResponse resp = getRespStructure();
        if (null != checkout) {
            resp.setOrder(checkout);
            return ResponseEntity.ok().body(resp);
        } else {
            throw new IllegalArgumentException("Failed to order");
        }
    }

    @GetMapping(path = "/Cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getCartItems(@RequestParam(value = "userId",required = true) Integer userId){
        CommonResponse respStructure = getRespStructure();
        List<CartInfoBean> cartItems = service.getCartItems(userId);
        if(null != cartItems && !cartItems.isEmpty()) {
            respStructure.setItems(cartItems);
            return ResponseEntity.ok().body(respStructure);
        } else {
            respStructure.setItems(Collections.emptyList());
            return ResponseEntity.ok().body(respStructure);
        }

    }

    @GetMapping(path = "/Cart/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> getCartItem(@PathVariable(value = "id",required = true) Integer id){
        CommonResponse respStructure = getRespStructure();
        CartInfoBean cartItems = service.getCartItem(id);
        if(null != cartItems ) {
            respStructure.setItem(cartItems);
            return ResponseEntity.ok().body(respStructure);
        } else {
            respStructure.setItems(Collections.emptyList());
            return ResponseEntity.ok().body(respStructure);
        }

    }

    @PutMapping(path = "/Cart", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> updateCart(@RequestBody CartInfoBean bean) {
        CommonResponse resp = getRespStructure();
        if (service.updateItem(bean)) {
            resp.setStatus("SUCCESS");
            return ResponseEntity.ok().body(resp);
        } else {
            resp.setStatus("FAILED");
            return ResponseEntity.badRequest().body(resp);
        }
    }

    @DeleteMapping(path = "/Cart/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> removeCartItem(@PathVariable(value = "id",required = true) Integer id){
        CommonResponse respStructure = getRespStructure();
        boolean cartItems = service.removeCartItem(id);
        if (cartItems ) {
            return ResponseEntity.ok().body(respStructure);
        } else {
            respStructure.setItems(Collections.emptyList());
            return ResponseEntity.ok().body(respStructure);
        }

    }


    private CommonResponse getRespStructure() {
        return new CommonResponse();
    }
}
