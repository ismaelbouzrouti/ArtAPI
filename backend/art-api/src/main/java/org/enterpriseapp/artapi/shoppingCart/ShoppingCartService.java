package org.enterpriseapp.artapi.shoppingCart;

import jakarta.transaction.Transactional;
import org.enterpriseapp.artapi.mapper.Imapper;
import org.enterpriseapp.artapi.products.Product;
import org.enterpriseapp.artapi.products.ProductRepository;
import org.enterpriseapp.artapi.reservation.ReservationService;
import org.enterpriseapp.artapi.users.User;
import org.enterpriseapp.artapi.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ShoppingCartService implements Imapper<ShoppingCart, ShoppingCartDTO> {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    ProductRepository productRepository;


    //first check if this user already has a shopping cart
    //if not a new one is created an assigned to the user with a default return date
    public ShoppingCartDTO getShoppingCart(Long userId){

        try{
            if(shoppingCartRepository.getShoppingCartByUserId(userId) == null){
                ShoppingCart shoppingCart = new ShoppingCart();

                Optional<User> optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()){
                    User user = optionalUser.get();
                    shoppingCart.setUser(user); //assigning shopping cart to user
                    shoppingCart.setReturnDate(setDefaultShoppingCartReturnDate()); //assigning default return date to shopping cart
                }
                return convertToDTO(shoppingCart);
            }else {
                ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(userId);

                //make sure the chosen date stays when there are items in the cart
                //if not put the date back to default 7 days
                if(shoppingCart.getCartItems().isEmpty()){
                    shoppingCart.setReturnDate(setDefaultShoppingCartReturnDate());
                }
                return convertToDTO(shoppingCart);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //first check if the user has a shopping cart
    //if not one is made and assigned to the user
    //the cart item is assigned to the shopping cart
    public void addItemToShoppingCart(CartItemDTO dto, long userId){

        try{

            CartItem cartItem = cartItemService.convertToEntity(dto);

            ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(userId);

            if( shoppingCart == null){
                shoppingCart = new ShoppingCart();
                System.out.println("shopping cart created");

                shoppingCart.setUser(userRepository.getReferenceById(userId)); //shopping cart assigned to user
                System.out.println(" you know");
                shoppingCart.setReturnDate(setDefaultShoppingCartReturnDate()); //default return date assigned to shopping cart
                shoppingCartRepository.save(shoppingCart);

                cartItem.setShoppingCart(shoppingCart); //assign cart item to shopping cart
                cartItemRepository.save(cartItem);
                shoppingCart.cartItems.add(cartItem); // add it to the list
                updateShoppingCart(shoppingCart);

                System.out.println("item added to cart");

            }else{
                //if the user already has a shopping cart check if that product is already in the cart

                //check if the current cart item's product is equal to the product of one of the shopping cart's items
                boolean exists = shoppingCart.getCartItems().stream()
                        .anyMatch(shoppingCartItem -> shoppingCartItem.getProduct().equals(cartItem.getProduct()));

                if(!exists) {
                    //if it doesn't exist just assign the current cart item to the shopping cart & add it to the list
                    cartItem.setShoppingCart(shoppingCart);
                    cartItemRepository.save(cartItem);
                    shoppingCart.cartItems.add(cartItem);
                    updateShoppingCart(shoppingCart);
                }else {
                    // if it does exist retrieve the existing item & increment it
                    CartItem alreadyExistingCartItem = shoppingCart.getCartItems().stream()
                                    .filter(shoppingCartItem -> shoppingCartItem.getProduct().equals(cartItem.getProduct()))
                                            .findFirst()
                                                    .orElseThrow(()-> new RuntimeException("cart item not found although it exists"));

                    incrementItem(alreadyExistingCartItem.getId());
                }

            }
            System.out.println("shopping cart updated and saved ");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    //when user adds the same product to the shopping cart or increments it
    //adding +1 to the item quantity
    public void incrementItem(Long itemId){

        try{
            //check if the item exits & retrieve it
            Optional<CartItem> item = cartItemRepository.findById(itemId);

            item.ifPresent(cartItem -> {
                if(cartItem.getQuantity() == cartItem.getProduct().getQuantity()){ //an item can't exceed the product quantity
                    System.out.println("can't have more than is in stock");
                    throw new ArithmeticException("can't have more than is in stock");
                }
                //increment & save
                System.out.println("item is present");
                cartItemService.incrementItemQuantity(cartItem);
                cartItemRepository.save(cartItem);
            });
            //update the shopping cart
            Optional <ShoppingCart> shoppingCart = shoppingCartRepository.findById(item.get().getShoppingCart().getId());
            System.out.println("shopping cart: "+shoppingCart);
            shoppingCart.ifPresent(cart ->{
                System.out.println("no problem");
                updateShoppingCart(cart);
            });

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    //when user decrements item
    // -1 to the item quantity
    public void decrementItem(Long itemId){

        //check if item exists
        Optional<CartItem> item = cartItemRepository.findById(itemId);

        //if its present check the quantity
        //if it's 1 remove it, can't reserve 0 of something  //the quantity is one and the user wants to decrement it once more
        item.ifPresent(cartItem -> {
            if(cartItem.getQuantity() == 1){
               removeItem(itemId);
            }else {
                // else decrement the quantity of the item & save
                cartItemService.decrementItemQuantity(cartItem);
                cartItemRepository.save(cartItem);
            }
        });
        //update the shopping cart
        Optional <ShoppingCart> shoppingCart = shoppingCartRepository.findById(item.get().getShoppingCart().getId());
        System.out.println("shopping cart: "+shoppingCart);
        shoppingCart.ifPresent(cart ->{
            System.out.println("no problem");
            updateShoppingCart(cart);
        });

    }

    public void removeItem(Long itemId){

        //check if item exists & delete it & update shopping cart
        try{
            Optional<CartItem> item = cartItemRepository.findById(itemId);
            item.ifPresent(cartItem -> {
                ShoppingCart shoppingCart = cartItem.getShoppingCart();
                cartItemRepository.delete(cartItem);
                updateShoppingCart(shoppingCart);
            });

        }catch (Exception e){
            throw new NoSuchElementException(e);
        }

    }

    //when user modifies the return date
    public void changeReturnDate(String date, Long userId){

        try {

            String sanitizedDate = date.strip().replace("\"", ""); //make sure the string is right for parsing
            LocalDate returnDate = LocalDate.parse(sanitizedDate); //turn the date string in Local date

            //check if shopping cart is not null
            ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(userId);

            if (shoppingCart != null) {
                //modify the return date & save
                shoppingCart.setReturnDate(returnDate);
                updateShoppingCart(shoppingCart);

            }else throw new NoSuchElementException("shopping cart doesn't exist");

        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new NoSuchElementException(e);
        }


    }

    //when user checks out //reserves his shopping cart
    public void checkout(Long userId){
        //check if shopping cart exists
        ShoppingCart shoppingCart = shoppingCartRepository.getShoppingCartByUserId(userId);

        if (shoppingCart==null){
            throw new NoSuchElementException("shopping cart doesn't exist");
        }else {
            //get each product's stock that is in the shopping cart // decrement that stock with the amount that is in the shopping cart
            //that way the product stock stays up to date
            shoppingCart.getCartItems().stream()
                    .forEach(cartItem -> {

                        //get optional to check if product exists
                        Optional<Product> optionalProduct = productRepository.findById(cartItem.getProduct().getId());

                        if (optionalProduct.isPresent()) {
                            //if it exists decrement the product stock with the item quantity
                            Product product = optionalProduct.get();
                            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                            productRepository.save(product);
                        } else {
                            throw new NoSuchElementException("product not found");
                        }
                    });

            //set the shopping cart return date back to default (7 days) & save the shopping cart
            shoppingCart.setReturnDate(setDefaultShoppingCartReturnDate());
            shoppingCartRepository.save(shoppingCart);

            // turn the shopping cart into a reservation
            reservationService.saveShoppingCartToReservation(shoppingCart);
        }
    }


    //The shopping cart should always be updated when an item is added or incremented and decremented
    //this way the total price & total quantity will always be right
    private void updateShoppingCart(ShoppingCart shoppingCart){

        //first a sum of the items in the shopping cart and their respective quantities
        int totalQuantity = shoppingCart.getCartItems().stream()
                .mapToInt(cartItem -> cartItem.getQuantity())
                .sum();

        //then retrieve the amount of days the items will be rented
        int rentDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), shoppingCart.getReturnDate());

        //go over all the cart items and get their prices do the same with their quantities and multiply them with each other
        //get the sum of the results and multiply that with the rent days
        double totalPrice = shoppingCart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getPrice() * cartItem.getQuantity())
                .sum() * rentDays;


        shoppingCart.setQuantity(totalQuantity); //assign new quantity to shopping cart
        shoppingCart.setTotalPrice(totalPrice); //assign new total price to shopping cart
        shoppingCartRepository.save(shoppingCart); //save the updated shopping cart to the db
    }

    //7 days rent is the default rent time
    public LocalDate setDefaultShoppingCartReturnDate(){

        return LocalDate.now().plusDays(7);
    }

    @Override
    public ShoppingCart convertToEntity(ShoppingCartDTO dto) {
        ShoppingCart shoppingCart = new ShoppingCart();

        shoppingCart.setId(dto.getId());
        shoppingCart.setUser(userRepository.getReferenceById(dto.getUserId()));
        shoppingCart.setQuantity(dto.getQuantity());
        shoppingCart.setTotalPrice(dto.getTotalPrice());
        shoppingCart.setReturnDate(LocalDate.parse(dto.getReturnDate())); //return date in dto is a string //client expects a string

        //turn cart items into entity themselves before setting them as items for the shopping cart entity
        //go over every cart item in the list and convert each one to an entity
        shoppingCart.setCartItems(dto.getCartItemDTOList().stream()
                .map(cartItemDTO -> cartItemService.convertToEntity(cartItemDTO))
                .toList());

        return shoppingCart;
    }

    @Override
    public ShoppingCartDTO convertToDTO(ShoppingCart entity) {
        ShoppingCartDTO dto = new ShoppingCartDTO();

        dto.setId(entity.getId());
        dto.setUserId(entity.getUser().getId());
        dto.setQuantity(entity.getQuantity());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setReturnDate(entity.getReturnDate().toString());

        //turn cart items into dto themselves before setting them as items for the shopping cart dto
        //go over every cart item in the list and convert each one to a dto
        dto.setCartItemDTOList(entity.getCartItems().stream()
                .map(cartItem -> cartItemService.convertToDTO(cartItem))
                .toList());

        return dto;
    }
}
