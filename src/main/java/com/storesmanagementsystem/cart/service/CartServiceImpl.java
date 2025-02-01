package com.storesmanagementsystem.cart.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storesmanagementsystem.cart.client.OrderServiceClient;
import com.storesmanagementsystem.cart.client.ProductServiceClient;
import com.storesmanagementsystem.cart.client.UserServiceClient;
import com.storesmanagementsystem.cart.contracts.*;
import com.storesmanagementsystem.cart.domain.CartDetails;
import com.storesmanagementsystem.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    ProductServiceClient productServiceClient;

    @Autowired
    OrderServiceClient orderServiceClient;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public boolean addToCart(CartInfoBean bean) {
        UserInfoBean user = getUser(bean.getUserId());
        if (user.getRole().equals("ROLE_DEALER")) {
            ProductInfoBean product = getProduct(bean.getItemProductId());
            if (product.getQuantity() > bean.getQuantity()) {
                CartDetails cartItem = new CartDetails();
                cartItem.setItemProductId(product.getId());
                cartItem.setRole(user.getRole());
                cartItem.setQuantity(bean.getQuantity());
                cartItem.setUserId(user.getId());
                cartItem.setImageUrl(product.getImageUrl());
                cartItem.setProductCost(product.getProductCost());
                cartItem.setProductName(product.getProductName());
                cartRepository.save(cartItem);
                return true;
            } else {
                throw new IllegalArgumentException("Specified quantity is more than available quantity");
            }

        } else if (user.getRole().equals("ROLE_CUSTOMER")) {
            DealerProductInfoBean product = getDealerProduct(bean.getItemProductId());
            if (product.getQuantity() > bean.getQuantity()) {
                CartDetails cartItem = new CartDetails();
                cartItem.setItemProductId(product.getId());
                cartItem.setRole(user.getRole());
                cartItem.setQuantity(bean.getQuantity());
                cartItem.setUserId(user.getId());
                cartItem.setImageUrl(product.getImageUrl());
                cartItem.setProductCost(product.getSellingPrice());
                cartItem.setProductName(product.getProductName());
                cartRepository.save(cartItem);
                return true;
            } else {
                throw new IllegalArgumentException("Specified quantity is more than available quantity");
            }

        }
        return false;
    }

    @Override
    public List<CartInfoBean> getCartItems(int userId) {
        List<CartDetails> allByUserId = cartRepository.findAllByUserId(userId);
        if (null != allByUserId && !allByUserId.isEmpty()) {
            return allByUserId.stream().map(item -> mapper.convertValue(item, CartInfoBean.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public CartInfoBean getCartItem(int itemId) {
        Optional<CartDetails> byId = cartRepository.findById(itemId);
        if(byId.isPresent()) {
            return mapper.convertValue(byId.get(),CartInfoBean.class);
        } else {
            throw new IllegalArgumentException("No item found in cart");
        }
    }

    @Override
    public boolean removeCartItem(int itemId) {
        Optional<CartDetails> byId = cartRepository.findById(itemId);
        if (byId.isPresent()) {
            cartRepository.deleteById(itemId);
            return true;
        } else {
            throw new IllegalArgumentException("Item not found in cart");
        }
    }

    @Override
    public OrderInfo checkout(CartInfoBean checkout) {
        UserInfoBean user = getUser(checkout.getUserId());
        Optional<CartDetails> byId = cartRepository.findById(checkout.getId());
        if(byId.isPresent()) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUser(user);
            orderInfo.setQuantity(checkout.getQuantity());
            orderInfo.setProductId(checkout.getItemProductId());
            orderInfo.setPaymentType(checkout.getPaymentType());
            CommonResponse commonResponse = orderServiceClient.placeOrder(orderInfo);
            if (null != commonResponse && null != commonResponse.getOrder()) {
                cartRepository.deleteById(checkout.getId());
                return commonResponse.getOrder();
            }
        }
        return null;
    }

    @Override
    public boolean updateItem(CartInfoBean bean) {
        Optional<CartDetails> byId = cartRepository.findById(bean.getId());
        if(byId.isPresent()) {
            CartDetails cartDetails = byId.get();
            cartDetails.setQuantity(bean.getQuantity());
            cartRepository.save(cartDetails);
            return true;
        } else {
            throw new IllegalArgumentException("Item not found in cart");
        }
    }

    private UserInfoBean getUser(Integer userId) {
        if (null != userId) {
            CommonResponse response = userServiceClient.getUser(userId);
            UserInfoBean user = response.getUser();
            if (null != user) {
                return user;
            } else {
                throw new IllegalArgumentException("User not found");
            }
        } else {
            throw new IllegalArgumentException("User Details are mandatory");
        }
    }

    private ProductInfoBean getProduct(Integer productId) {
        CommonResponse productResponse = productServiceClient.getProduct(productId);
        ProductInfoBean product = productResponse.getProduct();
        if (null != product) {
            return product;
        } else {
            throw new IllegalArgumentException("Failed to get product details");
        }
    }

    private DealerProductInfoBean getDealerProduct(Integer productId) {
        CommonResponse productResponse = productServiceClient.getDealerProduct(productId);
        DealerProductInfoBean product = productResponse.getDealerProd();
        if (null != product) {
            return product;
        } else {
            throw new IllegalArgumentException("Failed to get product details");
        }
    }
}
