package com.E_Commerce.dto.response;

import java.util.List;

public class ProductPageResponse {
    private List<ProductResponse> products;
    private Integer page;
    private Integer totalPages;
    private Long totalProducts;

    public List<ProductResponse> getProducts() { return products; }
    public void setProducts(List<ProductResponse> products) { this.products = products; }

    public Integer getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public Long getTotalProducts() { return totalProducts; }
    public void setTotalProducts(long totalProducts) { this.totalProducts = totalProducts; }
}
