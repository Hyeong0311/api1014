package org.hyeong.api1014.product.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.hyeong.api1014.category.domain.QCategoryProduct;
import org.hyeong.api1014.common.dto.PageResponseDTO;
import org.hyeong.api1014.product.domain.Product;
import org.hyeong.api1014.product.domain.QAttachFile;
import org.hyeong.api1014.product.domain.QProduct;
import org.hyeong.api1014.product.domain.QReview;
import org.hyeong.api1014.product.dto.ProductListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.hyeong.api1014.common.dto.PageRequestDTO;


import java.util.ArrayList;
import java.util.List;


@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {

    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public Page<Product> list(Pageable pageable) {

        log.info("------------list-----------------");

        QProduct product = QProduct.product;
        QReview review = QReview.review;
        QAttachFile attachFile = QAttachFile.attachFile;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(review).on(review.product.eq(product));
        query.leftJoin(product.attachFiles, attachFile);

        query.where(attachFile.ord.eq(0));

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<Tuple> tupleQuery =
                query.select(
                        product,
                        review.count(),
                        attachFile.filename
                );

        tupleQuery.fetch();

        return null;
    }

    @Override
    public PageResponseDTO<ProductListDTO> listByCno(Long cno, PageRequestDTO pageRequestDTO) {

        log.info("------------listByCno-----------------");

        Pageable pageable =
                PageRequest.of(pageRequestDTO.getPage() - 1,
                        pageRequestDTO.getSize(),
                        Sort.by("pno").descending());

        QProduct product = QProduct.product;
        QReview review = QReview.review;
        QAttachFile attachFile = QAttachFile.attachFile;
        QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(review).on(review.product.eq(product));
        query.leftJoin(product.attachFiles, attachFile);
        query.leftJoin(categoryProduct).on(categoryProduct.product.eq(product));

        query.where(attachFile.ord.eq(0));
        query.where(categoryProduct.category.cno.eq(cno));

        query.groupBy(product);

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<Tuple> tupleQuery =
                query.select(
                        product,
                        review.count(),
                        attachFile.filename
                );

        List<Tuple> tupleList = tupleQuery.fetch();

        log.info(tupleList);

        if(tupleList.isEmpty()) {
            return null;
        }

        List<ProductListDTO> dtoList = new ArrayList<>();

        tupleList.forEach(t -> {

           Product productObj = t.get(0, Product.class);
           Long count = t.get(1, Long.class);
           String filename = t.get(2, String.class);

           ProductListDTO dto = ProductListDTO.builder()
                   .pno(productObj.getPno())
                   .name(productObj.getName())
                   .price(productObj.getPrice())
                   .tags(productObj.getTags().stream().toList())
                   .fileName(filename)
                   .reviewCnt(count)
                   .build();

           dtoList.add(dto);
        });

        long total = tupleQuery.fetchCount();

        return PageResponseDTO.<ProductListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
