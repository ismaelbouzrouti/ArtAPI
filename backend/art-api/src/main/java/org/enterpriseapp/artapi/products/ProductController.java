package org.enterpriseapp.artapi.products;

import org.springframework.beans.factory.annotation.Autowired;
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
    public void createProduct(@RequestBody ProductDTO dto){
        System.out.println("in controller: " + dto.getCategory());
        service.createProduct(dto);
    }

    @PutMapping("/{id}")
    public void updateProduct(@RequestBody ProductDTO dto, @PathVariable long id){

        service.updateProduct(dto,id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id){

        service.deleteProduct(id);
    }
}
