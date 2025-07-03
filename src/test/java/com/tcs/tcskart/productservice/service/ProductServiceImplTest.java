package com.tcs.tcskart.productservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.tcs.tcskart.product.entity.*;
import com.tcs.tcskart.product.exceptions.UserNotFound;
import com.tcs.tcskart.product.repository.*;
import com.tcs.tcskart.product.service.ProductServiceImpl;
import com.tcs.tcskart.product.utility.ProductCategory;
import com.tcs.tcskart.product.utility.ProductNotFoundException;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepo userRepo;

    @Mock
    private OrderRepo orderRepo;

    @Mock
    private ProductReviewRepo productReviewRepo;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testViewAllProducts() {
        Product p1 = new Product();
        p1.setQuantity(5);
        Product p2 = new Product();
        p2.setQuantity(0);
        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        List<Product> result = productService.viewAllProducts();
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).getQuantity());
    }

    @Test
    void testViewProductsByNameFound() {
        Product p = new Product();
        p.setProductName("iphone13");
        when(productRepository.findByProductName("iphone13")).thenReturn(List.of(p));
        List<Product> result = productService.viewProductsByName("iphone13");
        assertFalse(result.isEmpty());
        assertEquals("iphone13", result.get(0).getProductName());
    }

    @Test
    void addProductSuccess() {
        Product product = new Product();
        product.setProductName("NewProduct");
        product.setProductRating(null);
        ProductImage image = new ProductImage();
        image.setImageUrl("image.jpg");
        image.setProduct(product);
        product.setImages(List.of(image));
        when(productRepository.findByProductName("NewProduct")).thenReturn(Collections.emptyList());
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Product saved = productService.addProduct(product);
        assertNotNull(saved);
        assertEquals("NewProduct", saved.getProductName());
        assertEquals(0.0, saved.getProductRating());
        assertEquals(product, saved.getImages().get(0).getProduct());
    }

    @Test
    void addProductWhenNameEmpty() {
        Product product = new Product();
        product.setProductName("  ");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(product);
        });
        assertEquals("Enter Something in Product Name.", exception.getMessage());
    }

    @Test
    void addProductReturnsNullWhenProductExists() {
        Product product = new Product();
        product.setProductName("HP Laptop");
        when(productRepository.findByProductName("HP Laptop")).thenReturn(List.of(new Product()));
        Product result = productService.addProduct(product);
        assertNull(result);
    }

    @Test
    void deleteProductByNameSuccess() {
        Product product = new Product();
        when(productRepository.findByProductName("lenovo")).thenReturn(List.of(product));
        productService.deleteProductByName("lenovo");
        verify(productRepository).deleteAll(List.of(product));
    }

    @Test
    void deleteProductByNameWhenNotFound() {
        when(productRepository.findByProductName("NoProduct")).thenReturn(Collections.emptyList());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductByName("NoProduct"));
    }

    @Test
    void deleteProductByIDSuccess() {
        Product product = new Product();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        productService.deleteById(1);
        verify(productRepository).delete(product);
    }

    @Test
    void deleteProductByIDWhenNotFound() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteById(99));
    }

    @Test
    void searchProductsByKeywordReturnsResults() {
        Product p1 = new Product();
        p1.setProductName("Phone");
        Product p2 = new Product();
        p2.setDescription("Smartphone");
        List<Product> products = List.of(p1, p2);
        when(productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase("phone", "phone"))
            .thenReturn(products);
        List<Product> result = productService.searchProductsByKeyword("phone");
        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
    }

    @Test
    void searchProductsByKeywordReturnsEmptyList() {
        when(productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase("good", "good"))
            .thenReturn(Collections.emptyList());
        List<Product> result = productService.searchProductsByKeyword("good");
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPaginatedProducts() {
        int page = 0;
        int size = 2;
        Product p1 = new Product();
        Product p2 = new Product();
        List<Product> productList = List.of(p1, p2);
        Page<Product> productPage = new PageImpl<>(productList);
        when(productRepository.findAll(PageRequest.of(page, size))).thenReturn(productPage);
        Page<Product> result = productService.getPaginatedProducts(page, size);
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void testIsValidImageUrl() {
        assertTrue(productService.isValidImageUrl("test.jpg"));
        assertTrue(productService.isValidImageUrl("test.jpeg"));
        assertTrue(productService.isValidImageUrl("test.PNG"));
        assertFalse(productService.isValidImageUrl("test.gif"));
        assertFalse(productService.isValidImageUrl(null));
        assertFalse(productService.isValidImageUrl("test"));
    }

    @Test
    void updateProductById_Success() {
        Integer productId = 1;
        Product existingProduct = new Product();
        existingProduct.setProductName("OldName");
        existingProduct.setDescription("OldDesc");
        existingProduct.setProductPrice(100.0);
        existingProduct.setQuantity(10);
        existingProduct.setProductRating(3.5);
        existingProduct.setImages(new ArrayList<>());

        ProductImage newImage = new ProductImage();
        newImage.setImageUrl("image.jpg");

        Product updatedProduct = new Product();
        updatedProduct.setProductName("NewName");
        updatedProduct.setDescription("NewDesc");
        updatedProduct.setProductPrice(150.0);
        updatedProduct.setQuantity(20);
        updatedProduct.setProductRating(4.5);
        updatedProduct.setImages(List.of(newImage));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product result = productService.updateProductById(productId, updatedProduct);

        assertEquals("NewName", result.getProductName());
        assertEquals("NewDesc", result.getDescription());
        assertEquals(150.0, result.getProductPrice());
        assertEquals(20, result.getQuantity());
        assertEquals(4.5, result.getProductRating());
        assertEquals(1, result.getImages().size());
        assertEquals(result, result.getImages().get(0).getProduct());
    }

    @Test
    void addProductReviewWhenRatingInvalid() {
        String result = productService.addProductReview("test@gmail.com", 1, 0.5, "Nice product");
        assertEquals("Rating must be between 1 and 5.", result);
    }

    @Test
    void addProductReviewWhenUserNotFound() {
        when(userRepo.findByEmail("test@gmail.com")).thenReturn(null);
        assertThrows(UserNotFound.class, () -> {
            productService.addProductReview("test@gmail.com", 1, 4, "Good");
        });
    }

    @Test
    void addProductReviewWhenNoDeliveredOrder() {
        User user = new User();
        when(userRepo.findByEmail("user@gmail.com")).thenReturn(user);
        when(orderRepo.findByUserEmail("user@gmail.com")).thenReturn(Collections.emptyList());
        String result = productService.addProductReview("user@gmail.com", 1, 4, "Good");
        assertEquals("You can only review products you have received.", result);
    }

    @Test
    void addProductReview_Success() {
        User user = new User();
        when(userRepo.findByEmail("user@gmail.com")).thenReturn(user);

        OrderItem item = new OrderItem();
        Product product = new Product();
        product.setProductId(1);
        item.setProduct(product);

        Order order = new Order();
        order.setStatus("DELIVERED");
        order.setOrderItems(List.of(item));

        when(orderRepo.findByUserEmail("user@gmail.com")).thenReturn(List.of(order));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productReviewRepo.save(any(ProductReview.class))).thenReturn(new ProductReview());

        when(productReviewRepo.findByProductProductId(1)).thenReturn(List.of(
            new ProductReview() {{ setRating(4.0); }},
            new ProductReview() {{ setRating(5.0); }}
        ));
        when(productRepository.save(product)).thenReturn(product);

        String result = productService.addProductReview("user@gmail.com", 1, 5, "Excellent!");
        assertEquals("Product review submitted successfully and rating updated.", result);
    }

    @Test
    void getReviewsByProductId_ReturnsList() {
        List<ProductReview> reviews = List.of(new ProductReview(), new ProductReview());
        when(productReviewRepo.findByProductProductId(1)).thenReturn(reviews);
        List<ProductReview> result = productService.getReviewsByProductId(1);
        assertEquals(2, result.size());
    }

    @Test
    void getProductsSortedByRating_ReturnsSortedProducts() {
        Product p1 = new Product();
        p1.setProductName("Product A");
        p1.setProductRating(4.5);

        Product p2 = new Product();
        p2.setProductName("Product B");
        p2.setProductRating(3.8);

        List<Product> products = Arrays.asList(p1, p2);
        when(productRepository.findAllByOrderByProductRatingDesc()).thenReturn(products);
        List<Product> result = productService.getProductsSortedByRating();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product A", result.get(0).getProductName());
        assertEquals(4.5, result.get(0).getProductRating());
    }
    
    @Test
    void searchByProductCategory_ReturnsProducts() {
        ProductCategory category = ProductCategory.ELECTRONICS; // example category

        Product p1 = new Product();
        p1.setProductCategory(category);

        Product p2 = new Product();
        p2.setProductCategory(category);

        List<Product> products = List.of(p1, p2);

        when(productRepository.findByProductCategory(category)).thenReturn(products);

        List<Product> result = productService.searchByProductCategory(category);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getProductCategory() == category));
    }

}
