package org.enterpriseapp.artapi.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    //alle producten worden gehaald van de db en in een lijst gezet
    //van die lijst maken we een stream //elk product in die stream wordt omgezet in een DTO
    //de DTO's worden in een lijst gezet en gereturned
    public List<ProductDTO> getAllProducts(){

        try{
            return repository.findAll()
                    .stream()
                    .map(this::convertToDTO)
                    .toList();
        } catch (Exception e) {
            throw new IllegalStateException("Could not retrieve products from database",e);
        }

    }

    public ProductDTO getProductById(long id){

            try {
               return convertToDTO(repository.getReferenceById(id));
            } catch (Exception e) {
                throw new IllegalStateException("failed to find product in database", e);
            }
    }

    //alle producten voor de gegeven categorie worden gehaald van de db en in een lijst gezet
    //van die lijst maken we een stream //elk product in die stream wordt omgezet in een DTO
    //de DTO's worden in een lijst gezet en gereturned
    public List<ProductDTO> getAllProductsByCategory(String cat){
        Category category = Category.valueOf(cat.toUpperCase());

        try{
            return repository.findByCategory(category)
                    .stream()
                    .map(this::convertToDTO)
                    .toList();
        } catch (Exception e) {
            throw new IllegalStateException("Could not retrieve products by category from database",e);
        }

    }


    public void createProduct(ProductDTO dto){
        if(dto != null){
            Product product = convertToEntity(dto);
            try {
                repository.save(product);
            } catch (Exception e) {
                throw new IllegalStateException("failed to save product in database", e);
            }

        }
        else throw new IllegalArgumentException("ProductDTO cannot be null");

    }


    public void updateProduct(ProductDTO dto, long id){

        if(repository.existsById(id) && dto!= null) {
            Product product = convertToEntity(dto);

            try{
                repository.save(product);
            } catch (Exception e) {
                throw new IllegalStateException("failed to update product in database", e);
            }

        }
        else throw new RuntimeException("product with id: " + id + " not found");

    }

    public void deleteProduct(long id){

        if(repository.existsById(id)) {
            try {
                repository.deleteById(id);
            } catch (Exception e) {
                throw new IllegalStateException("failed to update product in database", e);
            }
        }

    }

    //enkel DTO's worden teruggestuurd naar de client

    public ProductDTO convertToDTO(Product product) {

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPricePerDay(product.getPricePerDay());
        dto.setQuantity(product.getQuantity());
        dto.setCategory(product.getCategory());
        return dto;
    }

    //enkel entities worden opgeslaan in de db

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setQuantity(dto.getQuantity());
        product.setPricePerDay(dto.getPricePerDay());
        return product;
    }

}
