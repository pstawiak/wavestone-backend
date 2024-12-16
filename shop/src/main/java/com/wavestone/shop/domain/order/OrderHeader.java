package com.wavestone.shop.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wavestone.shop.domain.customer.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class OrderHeader {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String description;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Customer customer;

	@Column(nullable = false)
	private LocalDateTime orderDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderHeaderStatus status;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderLine> orderLines;

	public void addLine(OrderLine line) {
		if (orderLines == null) {
			orderLines = new ArrayList<>();
		}
		orderLines.add(line);
		line.setOrder(this);
	}

}
