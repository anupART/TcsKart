package com.tcskart.order_services.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;


import com.tcskart.order_services.bean.Order;
import com.tcskart.order_services.bean.Product;
import com.tcskart.order_services.bean.User;
import com.tcskart.order_services.dao.CartItemRepo;
import com.tcskart.order_services.dao.CartRepo;
import com.tcskart.order_services.dao.OrderItemRepo;
import com.tcskart.order_services.dao.OrderRepo;
import com.tcskart.order_services.dao.ProductRepo;
import com.tcskart.order_services.dao.ProductReviewRepo;
import com.tcskart.order_services.dao.UserRepo;
import com.tcskart.order_services.dto.OrderDto;
import com.tcskart.order_services.dto.OrderItemDto;
import com.tcskart.order_services.exception.NoProductFound;
import com.tcskart.order_services.exception.NotEnoughStock;
import com.tcskart.order_services.exception.ProductNotFound;
import com.tcskart.order_services.exception.UserNotFound;

@ExtendWith(MockitoExtension.class)
public class TestServices {
    
	@InjectMocks
	private OrderServices services;
	
	@Mock
	OrderRepo orderRepo;
	
	@Mock
	ProductReviewRepo reviewRepo;

	@Mock
	ProductRepo productPepo;

	@Mock
	UserRepo userRepo;

	@Mock
	OrderItemRepo itemRepo;

	@Mock
	CartRepo cartRepo;

	@Mock
	CartItemRepo cartItemRepo;
	
	@Mock
	private JavaMailSender javaMailSender;
	
	
	@Test
	public void testPlaceOrder()
	{
        User user = new User();
        user.setEmail("dhanarajsenthilkumar@gmail.com");
        user.setName("Dhanaraj");
        when(userRepo.findByEmail("dhanarajsenthilkumar@gmail.com")).thenReturn(user);
        OrderDto orderDto = new OrderDto();
        orderDto.setEmail("dhanarajsenthilkumar@gmail.com");
        orderDto.setAddress("Chennai");
        OrderItemDto itemDto = new OrderItemDto();
        itemDto.setProductid(1);
        itemDto.setQuantity(2);
        orderDto.setOrderItems(List.of(itemDto));
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Sample Product");
        product.setProductPrice(100.0);
        product.setQuantity(10);
        when(productPepo.findById(1)).thenReturn(Optional.of(product));
        Order savedOrder = new Order();
        savedOrder.setId(101);
        savedOrder.setUser(user);
        savedOrder.setAddress("Chennai");
        savedOrder.setOrderDate(LocalDate.now());
        savedOrder.setTotalAmount(200.0);

        when(orderRepo.save(Mockito.<Order>any())).thenReturn(savedOrder);
        Order result = services.PlaceOrder(orderDto);
        assertNotNull(result);
        assertEquals(101, result.getId());
        assertEquals(200.0, result.getTotalAmount());
        verify(userRepo).findByEmail("dhanarajsenthilkumar@gmail.com");
        verify(productPepo, times(2)).findById(1); 
        verify(orderRepo).save(Mockito.<Order>any());
		
	}
	
	@Test
	public void testPlaceOrderExceptionUSerNotFound()
	{
		User user=new User();
		user.setEmail("dhanarajsenthilkumar@gmail.com");
		OrderDto order=new OrderDto();
		order.setEmail("dhanarajsenthilkumar@gmail.com");
		assertThrows(UserNotFound.class,()->services.PlaceOrder(order));
	}
	@Test
	public void testplaceOrderProductNotFound()
	{
		 User user = new User();
	        user.setEmail("dhanarajsenthilkumar@gmail.com");
	        user.setName("Dhanaraj");
	        when(userRepo.findByEmail("dhanarajsenthilkumar@gmail.com")).thenReturn(user);
	        OrderDto orderDto = new OrderDto();
	        orderDto.setEmail("dhanarajsenthilkumar@gmail.com");
	        orderDto.setAddress("Chennai");
	        OrderItemDto itemDto = new OrderItemDto();
	        itemDto.setProductid(1);
	        itemDto.setQuantity(2);
	        orderDto.setOrderItems(List.of(itemDto));
	        Order savedOrder = new Order();
	        savedOrder.setId(101);
	        savedOrder.setUser(user);
	        savedOrder.setAddress("Chennai");
	        savedOrder.setOrderDate(LocalDate.now());
	        savedOrder.setTotalAmount(200.0);
	        assertThrows(ProductNotFound.class,()->services.PlaceOrder(orderDto));
	      
	}
	@Test
	public void testplaceOrderQualtity()
	{
		   User user = new User();
	        user.setEmail("dhanarajsenthilkumar@gmail.com");
	        user.setName("Dhanaraj");
	        when(userRepo.findByEmail("dhanarajsenthilkumar@gmail.com")).thenReturn(user);
	        OrderDto orderDto = new OrderDto();
	        orderDto.setEmail("dhanarajsenthilkumar@gmail.com");
	        orderDto.setAddress("Chennai");
	        OrderItemDto itemDto = new OrderItemDto();
	        itemDto.setProductid(1);
	        itemDto.setQuantity(2);
	        orderDto.setOrderItems(List.of(itemDto));
	        Product product = new Product();
	        product.setProductId(1);
	        product.setProductName("Sample Product");
	        product.setProductPrice(100.0);
	        product.setQuantity(1);
	        when(productPepo.findById(1)).thenReturn(Optional.of(product));
	        Order savedOrder = new Order();
	        savedOrder.setId(101);
	        savedOrder.setUser(user);
	        savedOrder.setAddress("Chennai");
	        savedOrder.setOrderDate(LocalDate.now());
	        savedOrder.setTotalAmount(200.0);

	        when(orderRepo.save(Mockito.<Order>any())).thenReturn(savedOrder);
	      
	        assertThrows(NotEnoughStock.class,()->services.PlaceOrder(orderDto));
	      
	}
	
	
	
	
	
	
}
