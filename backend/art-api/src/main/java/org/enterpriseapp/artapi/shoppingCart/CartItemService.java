package org.enterpriseapp.artapi.shoppingCart;

import org.enterpriseapp.artapi.mapper.Imapper;
import org.enterpriseapp.artapi.products.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService implements Imapper<CartItem, CartItemDTO> {

    @Autowired
   private ShoppingCartRepository shoppingCartRepository;

    @Autowired
   private ProductRepository productRepository;


    @Override
    public CartItem convertToEntity(CartItemDTO dto) {
        CartItem cartItem = new CartItem();

        cartItem.setId(dto.getId());
        cartItem.setShoppingCart(shoppingCartRepository.getShoppingCartByUserId(dto.getUserId()));
        cartItem.setPrice(dto.getPrice());
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setProduct(productRepository.getReferenceById(dto.getProductId()));

        return cartItem;

    }

    @Override
    public CartItemDTO convertToDTO(CartItem entity) {
        CartItemDTO dto = new CartItemDTO();

        dto.setId(entity.getId());
        dto.setShoppingCartId(entity.getShoppingCart().getId());
        dto.setProductId(entity.getProduct().getId());
        dto.setPrice(entity.getPrice());
        dto.setQuantity(entity.getQuantity());

        return dto;
    }
}
