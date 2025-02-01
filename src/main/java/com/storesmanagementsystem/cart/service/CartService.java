package com.storesmanagementsystem.cart.service;

import com.storesmanagementsystem.cart.contracts.CartInfoBean;
import com.storesmanagementsystem.cart.contracts.OrderInfo;

import java.util.List;

public interface CartService {

    public boolean addToCart(CartInfoBean bean);

    public List<CartInfoBean> getCartItems(int userId);

    public CartInfoBean getCartItem(int itemId);

    public boolean removeCartItem(int itemId);

    public OrderInfo checkout(CartInfoBean checkout);

    public boolean updateItem(CartInfoBean bean);

}
