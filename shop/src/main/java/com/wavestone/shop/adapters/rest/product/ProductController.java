package com.wavestone.shop.adapters.rest.product;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wavestone.shop.order.application.product.FindProductService;
import com.wavestone.shop.order.application.product.ManageProductService;
import com.wavestone.shop.order.application.product.ProductDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@RequiredArgsConstructor
public class ProductController {

	private final FindProductService findProductService;
	private final ManageProductService manageProductService;
	private final ProductCreateDtoMapper productCreateDtoMapper;

	@GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<ProductDto> getProducts() {
		return findProductService.findAllProducts();
	}

	@PostMapping(path = "/products")
	public ResponseEntity<Long> createProduct(@RequestBody ProductCreateDto product) {
		ProductDto productDto = productCreateDtoMapper.toProductDto(product);
		Long productId = manageProductService.storeProduct(productDto);

		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest()
						.path("/{id}")
						.buildAndExpand(productId)
						.toUri()
		).body(productId);
	}

	@GetMapping(path = "/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDto getProductById(@PathVariable Long productId) {
		return findProductService.getProductById(productId);
	}

}
