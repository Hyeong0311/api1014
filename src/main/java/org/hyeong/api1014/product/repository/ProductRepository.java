package org.hyeong.api1014.product.repository;

import org.hyeong.api1014.product.domain.Product;
import org.hyeong.api1014.product.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {
}
