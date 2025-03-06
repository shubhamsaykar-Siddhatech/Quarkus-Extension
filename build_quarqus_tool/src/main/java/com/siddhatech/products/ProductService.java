package com.siddhatech.products;

import java.util.List;

public interface ProductService {

    Product getProductById(long id);

    List<Product> getAllProducts();

    Product updateProduct(long id, Product product);

    Product saveProduct(Product product);

    void deleteProduct(long id);
}
