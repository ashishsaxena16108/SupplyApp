package com.dms.supplymanager.controllers;

import com.dms.supplymanager.entities.Products;
import com.dms.supplymanager.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/all")
    public ResponseEntity<List<Products>> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id){
        Optional<Products> product = productRepository.findById(id);
        if(product.isEmpty())
            return ResponseEntity.badRequest().body("Product not exist");
        return ResponseEntity.ok(product.get());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addNewProduct(@RequestBody Products product){
        Products product1;
        try{
            product1=productRepository.save(product);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Some Error has occurred "+e.getMessage());
        }
        return ResponseEntity.ok(product1);
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editProduct(String id,@RequestBody Products product){
        Optional<Products> existedProduct = productRepository.findById(id);
        if(existedProduct.isEmpty())
            return ResponseEntity.badRequest().body("Product not existed");
        existedProduct.get().setProductName(product.getProductName());
        existedProduct.get().setSku(product.getSku());
        existedProduct.get().setPrice(product.getPrice());
        existedProduct.get().setWeight(product.getWeight());
        existedProduct.get().setLength(product.getLength());
        existedProduct.get().setHeight(product.getHeight());
        existedProduct.get().setWidth(product.getWidth());
        return ResponseEntity.ok(productRepository.save(existedProduct.get()));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        try{
            productRepository.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Some error generated while deleting product");
        }
        return ResponseEntity.ok("Product deleted successfully");
    }
}
