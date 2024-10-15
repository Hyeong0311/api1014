package org.hyeong.api1014.category.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hyeong.api1014.product.domain.Product;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"product", "category"})
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cpno;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Category category;
}
