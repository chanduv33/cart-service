package com.storesmanagementsystem.cart.repository;

import com.storesmanagementsystem.cart.domain.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartDetails, Integer> {

    List<CartDetails> findAllByUserId(Integer userId);
}
