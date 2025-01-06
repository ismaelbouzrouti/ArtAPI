package org.enterpriseapp.artapi.products;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService service;



    @GetMapping
    public List<ProductDTO> getAllProducts(){

        return service.getAllProducts();

    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable long id){
        return service.getProductById(id);

    }

    @GetMapping("/category")
    public List<ProductDTO> getAllProductsByCategory(@RequestParam String category){

        return service.getAllProductsByCategory(category);

    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void createProduct(@Valid @RequestBody ProductDTO dto){
        System.out.println("in controller: " + dto.getCategory());
        service.createProduct(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateProduct(@Valid @RequestBody ProductDTO dto, @PathVariable long id){
        service.updateProduct(dto,id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteProduct(@PathVariable long id){

        service.deleteProduct(id);
    }
}
