package com.tcskart.order_services.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;

import com.tcskart.order_services.bean.Order;
import com.tcskart.order_services.bean.OrderItem;
import com.tcskart.order_services.bean.Product;
import com.tcskart.order_services.bean.User;
import com.tcskart.order_services.dao.OrderItemRepo;
import com.tcskart.order_services.dao.OrderRepo;
import com.tcskart.order_services.dao.ProductRepo;
import com.tcskart.order_services.dao.UserRepo;
import com.tcskart.order_services.dto.OrderDto;
import com.tcskart.order_services.dto.OrderItemDto;
import com.tcskart.order_services.exception.NoOrder;
import com.tcskart.order_services.exception.NotEnoughStock;
import com.tcskart.order_services.exception.ProductNotFound;
import com.tcskart.order_services.exception.UserNotFound;

@Service
public class OrderServices {
	
	@Autowired 
	OrderRepo orderRepo;
	
	@Autowired 
	ProductRepo productPepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	OrderItemRepo itemRepo;
	
	public Order PlaceOrder(OrderDto orderdto)
	{
		    User user = userRepo.findByEmail(orderdto.getEmail());
		    //System.out.println(orderdto.getEmail());
		    if(user==null)
		    {
		    	throw new UserNotFound();
		    }

	        Order order = new Order();
	        order.setUser(user);
	        order.setAddress(orderdto.getAddress());
	        order.setStatus("PENDING");
	        LocalDate currentDate = LocalDate.now();
	        order.setOrderDate(currentDate);

	        List<OrderItem> items = new ArrayList<>();
            Double TotalPrice=0.0;
            for (OrderItemDto dto: orderdto.getOrderItems())
	        {
	        	Product product = productPepo.findById(dto.getProductid()).
	        			orElseThrow(() -> new ProductNotFound());
	            if (product.getQuantity() < dto.getQuantity()) {
	                throw new NotEnoughStock();
	            }
	        }
	        for (OrderItemDto dto: orderdto.getOrderItems())
	        {
	        	
	        	Product product = productPepo.findById(dto.getProductid()).
	        			orElseThrow(() -> new ProductNotFound());
	            product.setQuantity(product.getQuantity() - dto.getQuantity());
	            OrderItem item = new OrderItem();
	            item.setOrder(order);
	            item.setProduct(product);
	            item.setQuantity(dto.getQuantity());
	            item.setPrice(product.getProductPrice() * dto.getQuantity());
	            TotalPrice+=item.getPrice();
	            productPepo.save(product);
	            items.add(item);
	        }
	      
	        order.setTotalAmount(TotalPrice);
	        order.setOrderItems(items);
	        return orderRepo.save(order); 
	}
	public List<Order> getOrdersByUserEmail(String email) {
		User user = userRepo.findByEmail(email);
	    if(user==null)
	    {
	    	throw new UserNotFound();
	    }
	    List<Order> myOrders= orderRepo.findByUserEmail(email);
	    if(myOrders.size()==0)
	    {
	    	throw new NoOrder();
	    }
	    return myOrders;
	}
	public List<Order> getallorders()
	{
		List<Order> myOrders= orderRepo.findAll();
	    if(myOrders.size()==0)
	    {
	    	throw new NoOrder();
	    }
	    return myOrders;
	}
	public String updateStatus(int id,String status)
	{
		Order order=orderRepo.findById(id)
				.orElseThrow(() -> new NoOrder());
		order.setStatus(status);
		orderRepo.save(order);
		return "Sucessfully Update";
	}
}
