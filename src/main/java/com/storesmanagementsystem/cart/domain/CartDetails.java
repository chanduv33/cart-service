package com.storesmanagementsystem.cart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cart_info")
public class CartDetails {
	
	@Id
	@Column
	@GeneratedValue
	private int id;
	
	@Column
	private String role;

	@Column
	private Integer userId;
	
	@Column
	private int itemProductId;
	
	@Column
	private int quantity;

	@Column
	private String productName;

	@Column
	private Double productCost;

	@Lob @Basic(fetch = FetchType.EAGER)
	@Column(name = "imageUrl")
	private String imageUrl;

}
