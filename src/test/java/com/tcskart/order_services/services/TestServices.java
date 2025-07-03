package com.tcskart.order_services.services;

import com.tcskart.order_services.bean.*;

import com.tcskart.order_services.dao.*;

import com.tcskart.order_services.dto.*;

import com.tcskart.order_services.exception.*;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.PageRequest;

import org.springframework.mail.javamail.JavaMailSender;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TestServices {

	@InjectMocks
	private OrderServices orderServices;

	@Mock
	private OrderRepo orderRepo;

	@Mock
	private ProductReviewRepo reviewRepo;

	@Mock
	private ProductRepo productPepo;

	@Mock
	private UserRepo userRepo;

	@Mock
	private OrderItemRepo itemRepo;

	@Mock
	private CartRepo cartRepo;

	@Mock
	private CartItemRepo cartItemRepo;

	@Mock
	private JavaMailSender javaMailSender;

	private User testUser;

	private Product testProduct;

	@BeforeEach
	public void setup() {
		testUser = new User();
		testUser.setEmail("a@gmail.com");
		testUser.setName("iphone");
		testProduct = new Product();
		testProduct.setProductId(1);
		testProduct.setProductName("iphone");
		testProduct.setProductPrice(100.0);
		testProduct.setQuantity(10);
	}

	@Test
	public void testGetAllOrders_Success() {
		List<Order> orderList = List.of(new Order(), new Order());
		when(orderRepo.findAll()).thenReturn(orderList);
		List<Order> result = orderServices.getallorders();
		assertEquals(2, result.size());
		verify(orderRepo, times(1)).findAll();
	}

	@Test
	public void testGetAllOrders_NoOrdersFound() {
		when(orderRepo.findAll()).thenReturn(Collections.emptyList());
		assertThrows(NoOrder.class, () -> orderServices.getallorders());
		verify(orderRepo, times(1)).findAll();
	}

	@Test
	public void testPlaceOrder_UserNotFound() {
		when(userRepo.findByEmail(anyString())).thenReturn(null);
		OrderDto dto = new OrderDto();
		dto.setEmail("invalid@gmail.com");
		assertThrows(UserNotFound.class, () -> orderServices.PlaceOrder(dto));
	}

	@Test
	public void testPlaceOrder_ProductNotFound() {
		when(userRepo.findByEmail(anyString())).thenReturn(testUser);
		when(productPepo.findById(1)).thenReturn(Optional.empty());
		OrderDto dto = new OrderDto();
		dto.setEmail(testUser.getEmail());
		OrderItemDto item = new OrderItemDto();
		item.setProductid(1);
		item.setQuantity(1);
		dto.setOrderItems(List.of(item));
		assertThrows(ProductNotFound.class, () -> orderServices.PlaceOrder(dto));
	}

	@Test
	public void testPlaceOrder_NotEnoughStock() {
		testProduct.setQuantity(1);
		when(userRepo.findByEmail(anyString())).thenReturn(testUser);
		when(productPepo.findById(1)).thenReturn(Optional.of(testProduct));
		OrderDto dto = new OrderDto();
		dto.setEmail(testUser.getEmail());
		OrderItemDto item = new OrderItemDto();
		item.setProductid(1);
		item.setQuantity(5);
		dto.setOrderItems(List.of(item));
		assertThrows(NotEnoughStock.class, () -> orderServices.PlaceOrder(dto));
	}

	@Test
	public void testCartMoveToOrder_UserNotFound() {
		when(userRepo.findByEmail("invalid@gmail.com")).thenReturn(null);
		assertThrows(UserNotFound.class,
				() -> orderServices.cartMoveToOrder("invalid@gmail.com", "address"));
	}

	@Test
	public void testCartMoveToOrder_CartNotFound() {
		User user = new User();
		user.setEmail("a@gmail.com");
		when(userRepo.findByEmail("a@gmail.com")).thenReturn(user);
		when(cartRepo.findByUserEmail("a@gmail.com")).thenReturn(null);
		assertThrows(NoProductFound.class,
				() -> orderServices.cartMoveToOrder("a@gmail.com", "address"));
	}

	@Test
	public void testRemoveAllItemsFromCart_Success() {
		Integer cartId = 101;
		Product product1 = new Product();
		product1.setProductId(1);
		product1.setProductName("iphone");
		product1.setQuantity(5);
		CartItem cartItem1 = new CartItem();
		cartItem1.setId(1);
		cartItem1.setProduct(product1);
		cartItem1.setQuantity(3);
		List<CartItem> cartItems = List.of(cartItem1);
		when(cartItemRepo.findByCartId(cartId)).thenReturn(cartItems);
		orderServices.removeAllItemsFromCart(cartId);
		assertEquals(8, product1.getQuantity());
		verify(productPepo, times(1)).save(product1);
		verify(cartItemRepo, times(1)).deleteById(cartId);
	}

	@Test
	public void testGetOrdersByUserEmail_Success() {
		when(userRepo.findByEmail(anyString())).thenReturn(testUser);
		when(orderRepo.findByUserEmail(anyString())).thenReturn(List.of(new Order(), new Order()));
		List<Order> orders = orderServices.getOrdersByUserEmail("a@gmail.com");
		assertEquals(2, orders.size());
	}

	@Test
	public void testGetOrdersByUserEmail_NoOrders() {
		when(userRepo.findByEmail(anyString())).thenReturn(testUser);
		when(orderRepo.findByUserEmail(anyString())).thenReturn(Collections.emptyList());
		assertThrows(NoOrder.class, () -> orderServices.getOrdersByUserEmail("a@gmail.com"));
	}

	@Test
	public void testUpdateStatus_Success() {
		Order order = new Order();
		order.setId(1);
		order.setStatus("PENDING");
		when(orderRepo.findById(1)).thenReturn(Optional.of(order));
		String result = orderServices.updateStatus(1, "DELIVERED");
		assertEquals("Sucessfully Update", result);
		verify(orderRepo).save(order);
	}

	@Test
	public void testUpdateStatus_NoOrder() {
		when(orderRepo.findById(99)).thenReturn(Optional.empty());
		assertThrows(NoOrder.class, () -> orderServices.updateStatus(99, "SHIPPED"));
	}

	@Test
	public void testHighestSellingProduct_EmptyList() {
		when(itemRepo.findTopSellingProductsWithCount()).thenReturn(Collections.emptyList());
		assertThrows(NoProductFound.class, () -> orderServices.highestSellingProduct());
	}

	@Test
	public void testHighestSellingProductTop5_EmptyList() {
		when(itemRepo.findTopSellingProductsWithCountTop(any(PageRequest.class))).thenReturn(Collections.emptyList());
		assertThrows(NoProductFound.class, () -> orderServices.highestSellingProductTop5());

	}

	@Test
	public void testHighestSellingProductDistantUser_EmptyList() {
		when(itemRepo.findTopSellingProductsByDistinctUsers(any(PageRequest.class)))
				.thenReturn(Collections.emptyList());
		assertThrows(NoProductFound.class, () -> orderServices.highestSellingProductDistantUser());
	}
	
    @Test
    public void testAlertMail_Success() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Test Product");
        product.setQuantity(3);
        OrderServices spyService = spy(orderServices);
        doReturn("Mail Sent Successfully...").when(spyService).received(any(MailSend.class));
        spyService.AlertMail(product);
        verify(spyService, times(1)).received(any(MailSend.class));
    }
}
