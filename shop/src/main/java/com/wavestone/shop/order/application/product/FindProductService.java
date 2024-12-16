package com.wavestone.shop.order.application.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavestone.shop.domain.product.Product;
import com.wavestone.shop.domain.product.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	@Transactional(readOnly = true)
	public List<ProductDto> findAllProducts() {
		List<Product> products = productRepository.findAll();
		return productMapper.toDtoList(products);
	}

	@Transactional(readOnly = true)
	public ProductDto getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Product not found"));
		return productMapper.toDto(product);
	}

}
