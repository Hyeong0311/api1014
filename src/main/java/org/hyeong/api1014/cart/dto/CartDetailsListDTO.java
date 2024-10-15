package org.hyeong.api1014.cart.dto;

import lombok.Data;

@Data
public class CartDetailsListDTO {

    private Long pno;
    private String pname;
    private int price;
    private Long reviewCnt;
    private String fileName;
    private int qty;

    public CartDetailsListDTO(Long pno, String pname, int price, Long reviewCnt, String fileName, int qty) {
        this.pno = pno;
        this.pname = pname;
        this.price = price;
        this.reviewCnt = reviewCnt;
        this.fileName = fileName;
        this.qty = qty;
    }
}
