package com.storesmanagementsystem.cart.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartInfoBean {

	private Integer id;

	private Integer userId;

	private Integer itemProductId;

	private Integer quantity;

	private String imageUrl;

	private String paymentType;

	private String productName;

	private Double productCost;

}
