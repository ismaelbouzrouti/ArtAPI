package org.enterpriseapp.artapi.database;

import org.enterpriseapp.artapi.products.Product;
import org.enterpriseapp.artapi.products.ProductRepository;
import org.enterpriseapp.artapi.products.Category;
import org.enterpriseapp.artapi.users.User;
import org.enterpriseapp.artapi.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedUsers();
        seedProducts();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUserName("admin");
            admin.setPassword(passwordEncoder.encode("12345678"));
            admin.setEmail("admin@example.com");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setIsAdmin(true);

            User user = new User();
            user.setUserName("user");
            user.setPassword(passwordEncoder.encode("12345678"));
            user.setEmail("user@example.com");
            user.setFirstName("Regular");
            user.setLastName("User");
            user.setIsAdmin(false);

            userRepository.saveAll(List.of(admin, user));
            System.out.println("Users seeded successfully!");
        } else {
            System.out.println("Users already seeded.");
        }
    }

    private void seedProducts() {
        if (productRepository.count() == 0) {
            Product product1 = createProduct("HDMI Cable", "High-speed HDMI cable", Category.CABLE, 50, new BigDecimal("15.00"));
            Product product2 = createProduct("Ethernet Cable", "Cat6 Ethernet cable", Category.CABLE, 100, new BigDecimal("10.00"));
            Product product3 = createProduct("LED Lighting", "Portable LED light", Category.LIGHTING, 25, new BigDecimal("30.00"));
            Product product4 = createProduct("Ring Light", "Adjustable ring light", Category.LIGHTING, 20, new BigDecimal("45.00"));
            Product product5 = createProduct("Solar Panel 100W", "100W solar panel", Category.PANELS, 15, new BigDecimal("120.00"));
            Product product6 = createProduct("Solar Panel 200W", "200W solar panel", Category.PANELS, 10, new BigDecimal("220.00"));
            Product product7 = createProduct("Extension Cable", "5m extension cable", Category.CABLE, 40, new BigDecimal("12.00"));
            Product product8 = createProduct("Flood Light", "Outdoor flood light", Category.LIGHTING, 15, new BigDecimal("50.00"));
            Product product9 = createProduct("Flexible Solar Panel", "Flexible 50W solar panel", Category.PANELS, 8, new BigDecimal("150.00"));
            Product product10 = createProduct("Cable Organizer", "Cable management kit", Category.CABLE, 60, new BigDecimal("8.00"));

            productRepository.saveAll(List.of(
                    product1, product2, product3, product4, product5,
                    product6, product7, product8, product9, product10
            ));
            System.out.println("Products seeded successfully!");
        } else {
            System.out.println("Products already seeded.");
        }
    }

    private Product createProduct(String name, String description, Category category, int quantity, BigDecimal pricePerDay) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setPricePerDay(pricePerDay);
        return product;
    }
}
