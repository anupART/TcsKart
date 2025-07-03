package com.tcskart.order_services.controller;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.tcskart.order_services.bean.Order;

import com.tcskart.order_services.bean.Product;
import com.tcskart.order_services.bean.ProductNotAvailableLocation;
import com.tcskart.order_services.dto.OrderDto;

import com.tcskart.order_services.dto.OrderItemDto;
import com.tcskart.order_services.dto.ProductSalesByUser;
import com.tcskart.order_services.dto.ProductSalesDTO;

import com.tcskart.order_services.services.OrderServices;

@ExtendWith(SpringExtension.class)

@WebMvcTest(OrderController.class)

public class OrderControllerTest {

	@Autowired

	private MockMvc mockMvc;

	@MockBean

	private OrderServices orderServices;

	@Autowired

	private ObjectMapper objectMapper;

	@Test

	public void testPlaceOrder() throws Exception {
		OrderDto orderDto = new OrderDto();
		orderDto.setEmail("a@gmail.com");
		orderDto.setAddress("gayathri nagar");
		OrderItemDto itemDto = new OrderItemDto();
		itemDto.setProductid(1);
		itemDto.setQuantity(2);
		orderDto.setOrderItems(List.of(itemDto));
		Order mockOrder = new Order();
		mockOrder.setId(100);
		mockOrder.setOrderDate(LocalDate.now());
		mockOrder.setAddress("gayathri nagar");
		when(orderServices.PlaceOrder(any(OrderDto.class))).thenReturn(mockOrder);
		mockMvc.perform(post("/users/placeorder")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(orderDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(100))
				.andExpect(jsonPath("$.address").value("gayathri nagar"));

	}

	@Test
	public void testGetOrdersByEmail() throws Exception {
		Order order1 = new Order();
		order1.setId(101);
		Order order2 = new Order();
		order2.setId(102);
		when(orderServices.getOrdersByUserEmail("a@gmail.com")).thenReturn(List.of(order1, order2));
		mockMvc.perform(get("/users/myorders/a@gmail.com"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(101))
				.andExpect(jsonPath("$[1].id").value(102));

	}

	@Test
	public void testGetAllOrders() throws Exception {
		Order order1 = new Order();
		order1.setId(201);
		Order order2 = new Order();
		order2.setId(202);
		when(orderServices.getallorders()).thenReturn(List.of(order1, order2));
		mockMvc.perform(get("/admins/allorders"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id").value(201))
				.andExpect(jsonPath("$[1].id").value(202));
	}

	@Test
	public void testChangeStatus() throws Exception {
		when(orderServices.updateStatus(301, "DELIVERED")).thenReturn("Sucessfully Update");
		mockMvc.perform(get("/admins/updateorderstatus/301/DELIVERED"))
				.andExpect(status().isOk())
				.andExpect(content().string("Sucessfully Update"));
	}

	@Test
	public void testCartToMoveOrder() throws Exception {
		Order order = new Order();
		order.setId(401);
		when(orderServices.cartMoveToOrder("a@gmail.com", "gayathri nagar")).thenReturn(order);
		mockMvc.perform(get("/users/carttomoveorder/a@gmail.com/gayathri nagar"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(401));
	}

	@Test
	public void testHighestSellingProduct() throws Exception {
		Product product = new Product();
		product.setProductId(501);
		product.setProductName("iphone13");
		ProductSalesDTO dto = new ProductSalesDTO(product, 150L);
		when(orderServices.highestSellingProduct()).thenReturn(List.of(dto));
		mockMvc.perform(get("/admin/dashboard/ProductList"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].product.productName").value("iphone13"))
				.andExpect(jsonPath("$[0].totalQuantitySold").value(150));
	}

	@Test
	public void testHighestSellingProductTop5() throws Exception {
		Product product = new Product();
		product.setProductId(502);
		product.setProductName("iphone14");
		ProductSalesDTO dto = new ProductSalesDTO(product, 120L);
		when(orderServices.highestSellingProductTop5()).thenReturn(List.of(dto));
		mockMvc.perform(get("/admin/dashboard/Top5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].product.productName").value("iphone14"))
				.andExpect(jsonPath("$[0].totalQuantitySold").value(120));
	}
	
}
