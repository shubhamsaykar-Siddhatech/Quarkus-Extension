package com.siddhatech.products;

import com.siddhatech.exceptions.GlobalException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@ApplicationScoped
public class ProductServiceImpl implements ProductService {


    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    @Inject
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(long id) {

        return productRepository
                .findByIdOptional(id).
                orElseThrow(() -> new GlobalException(404,"ProductService:: Product not found.","Product with ID " + id + " not found"));

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    @Transactional
    @Override
    public Product updateProduct(long id, Product product) {
        try {
            log.info("check point to update product");
            Product existingUser = getProductById(id);
            existingUser.setName(product.getName());
            existingUser.setBrandName(product.getBrandName());
            existingUser.setPrice(product.getPrice());
            productRepository.persist(existingUser);
            log.info("Product updated.");
            return existingUser;

        } catch (GlobalException e) {
            log.warn(e.getMessage());
            throw new GlobalException(404,"ProductService:: Unable to update product.","Product with ID "+id+ " not found");
        }

    }

    @Transactional
    @Override
    public Product saveProduct(Product product) {
        productRepository.persist(product);
        return product;
    }

    @Transactional
    @Override
    public void deleteProduct(long id) {
        try {
        productRepository.delete(getProductById(id));
            }catch (GlobalException exception){
            throw  new GlobalException(404,"ProductService:: Product not found.","Unable to delete product with id "+id);
        }
    }
}
