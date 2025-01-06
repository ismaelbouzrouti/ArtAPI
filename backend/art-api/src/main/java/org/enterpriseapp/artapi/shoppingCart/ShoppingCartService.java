package org.enterpriseapp.artapi.shoppingCart;

import org.enterpriseapp.artapi.mapper.Imapper;
import org.enterpriseapp.artapi.users.User;
import org.enterpriseapp.artapi.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService implements Imapper<ShoppingCart, ShoppingCartDTO> {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemService cartItemService;

    public ShoppingCartDTO getShoppingCart(Long userId){

        return convertToDTO(shoppingCartRepository.getShoppingCartByUserId(userId));
    }


    public void createShoppingCart(long userId){
        ShoppingCart shoppingCart = new ShoppingCart();

        if (userRepository.existsById(userId)){
            shoppingCart.setUser(userRepository.getReferenceById(userId));
            shoppingCartRepository.save(shoppingCart);
        }


    }

    public void addItemToShoppingCart(CartItemDTO dto, long userId){

       ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(userId);

       shoppingCart.cartItems.add(cartItemService.convertToEntity(dto));


    }

    @Override
    public ShoppingCart convertToEntity(ShoppingCartDTO dto) {
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setId(dto.getId());
        shoppingCart.setUser(userRepository.getReferenceById(dto.getUserId()));
        shoppingCart.setQuantity(dto.getQuantity());
        shoppingCart.setTotalPrice(dto.getTotalPrice());

        return shoppingCart;
    }

    @Override
    public ShoppingCartDTO convertToDTO(ShoppingCart entity) {
        ShoppingCartDTO dto = new ShoppingCartDTO();

        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setQuantity(entity.getQuantity());
        dto.setTotalPrice(entity.getTotalPrice());

        return dto;
    }
}
