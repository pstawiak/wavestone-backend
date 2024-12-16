package com.wavestone.shop.domain.order;

import com.wavestone.shop.domain.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderLine {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String description;

	@Column(nullable = false)
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private OrderHeader order;

}
