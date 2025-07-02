package com.tcskart.order_services.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tcskart.order_services.bean.Cart;
import com.tcskart.order_services.bean.CartItem;
import com.tcskart.order_services.bean.Order;
import com.tcskart.order_services.bean.OrderItem;
import com.tcskart.order_services.bean.Product;

import com.tcskart.order_services.bean.ProductReview;
import com.tcskart.order_services.bean.User;
import com.tcskart.order_services.dao.CartItemRepo;
import com.tcskart.order_services.dao.CartRepo;
import com.tcskart.order_services.dao.OrderItemRepo;
import com.tcskart.order_services.dao.OrderRepo;
import com.tcskart.order_services.dao.ProductAvailableRepo;
import com.tcskart.order_services.dao.ProductRepo;
import com.tcskart.order_services.dao.ProductReviewRepo;
import com.tcskart.order_services.dao.UserRepo;
import com.tcskart.order_services.dto.MailSend;
import com.tcskart.order_services.dto.OrderDto;
import com.tcskart.order_services.dto.OrderItemDto;
import com.tcskart.order_services.dto.ProductSalesByUser;
import com.tcskart.order_services.dto.ProductSalesDTO;
import com.tcskart.order_services.exception.NoOrder;
import com.tcskart.order_services.exception.NoProductFound;
import com.tcskart.order_services.exception.NotEnoughStock;
import com.tcskart.order_services.exception.ProductNotAvailable;
import com.tcskart.order_services.exception.ProductNotFound;
import com.tcskart.order_services.exception.UserNotFound;

@Service
public class OrderServices {

	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	ProductReviewRepo reviewRepo;

	@Autowired
	ProductRepo productPepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	OrderItemRepo itemRepo;

	@Autowired
	CartRepo cartRepo;

	@Autowired
	CartItemRepo cartItemRepo;
	
	@Autowired
	ProductAvailableRepo availableRepo;
	
	@Autowired
	private JavaMailSender javaMailSender;

	public Order PlaceOrder(OrderDto orderdto) {
		User user = userRepo.findByEmail(orderdto.getEmail());
		// System.out.println(orderdto.getEmail());
		if (user == null) {
			throw new UserNotFound();
		}

		Order order = new Order();
		order.setUser(user);
		String addressShow=orderdto.getAddress()+" Pincode : "+Integer.toString(orderdto.getPincode());
		order.setAddress(addressShow);
		order.setStatus("PENDING");
		LocalDate currentDate = LocalDate.now();
		order.setOrderDate(currentDate);

		List<OrderItem> items = new ArrayList<>();
		Double TotalPrice = 0.0;
		for (OrderItemDto dto : orderdto.getOrderItems()) {
			Product product = productPepo.findById(dto.getProductid()).orElseThrow(() -> new ProductNotFound());
			if (product.getQuantity() < dto.getQuantity()) {
				this.AlertMail(product);
				throw new NotEnoughStock();
			}
		}
		ArrayList<String> ProductName = new ArrayList<>();
		for (OrderItemDto dto : orderdto.getOrderItems()) {

			Product product = productPepo.findById(dto.getProductid()).orElseThrow(() -> new ProductNotFound());
			if(availableRepo.existsByProduct_ProductIdAndPincode(dto.getProductid(),orderdto.getPincode()))
			{
				 throw new ProductNotAvailable(product.getProductName());
			}
			product.setQuantity(product.getQuantity() - dto.getQuantity());
			if (product.getQuantity() < 5) {
                   this.AlertMail(product);
			}
//			System.out.println(product.getProductName());
			ProductName.add(product.getProductName());
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setProduct(product);
			item.setQuantity(dto.getQuantity());
			item.setPrice(product.getProductPrice() * dto.getQuantity());
			TotalPrice += item.getPrice();
			productPepo.save(product);
			items.add(item);
		}

		order.setTotalAmount(TotalPrice);
		order.setOrderItems(items);
		MailSend mailContent = new MailSend();
		String recipient = user.getEmail();
		mailContent.setRecipient(recipient);
		String subject = "Order Confirmation - Thank you for your purchase!";
		mailContent.setSubject(subject);
		StringBuilder msgBody = new StringBuilder();
		Order orderConfrim = orderRepo.save(order);
		msgBody.append("Dear ").append(user.getName()).append(",\n\n")
				.append("Thank you for your order with TCSKart! 🎉\n\n").append("🛒 Order Details:\n")
				.append("Order ID     : ").append(orderConfrim.getId()).append("\n").append("Order Date   : ")
				.append(order.getOrderDate()).append("\n").append("Total Amount : ₹").append(order.getTotalAmount())
				.append("\n").append("Delivery To  : ").append(order.getAddress()).append("\n\n")
				.append("📦 Your items:\n");

		for (OrderItem item : items) {
			for (String a : ProductName) {
				msgBody.append("- ").append(a).append(" × ").append(item.getQuantity()).append(" = ₹")
						.append(item.getPrice()).append("\n");
			}
		}

		msgBody.append("\nWe will notify you once your order is shipped.\n")
				.append("If you have any questions, contact us at support@tcskart.com.\n\n")
				.append("Thanks,\nTCSKart Team");

		String finalMsgBody = msgBody.toString();
		mailContent.setMsgBody(finalMsgBody);
		String output = this.received(mailContent);
		// System.out.println(output);
		return orderConfrim;
	}

	public List<Order> getOrdersByUserEmail(String email) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			throw new UserNotFound();
		}
		List<Order> myOrders = orderRepo.findByUserEmail(email);
		if (myOrders.size() == 0) {
			throw new NoOrder();
		}
		return myOrders;
	}

	public List<Order> getallorders() {
		List<Order> myOrders = orderRepo.findAll();
		if (myOrders.size() == 0) {
			throw new NoOrder();
		}
		return myOrders;
	}

	public String updateStatus(int id, String status) {
		Order order = orderRepo.findById(id).orElseThrow(() -> new NoOrder());
		order.setStatus(status);
		orderRepo.save(order);
		return "Sucessfully Update";
	}

	public String received(MailSend mailContent) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom("sdhanush0048@gmail.com");
			mailMessage.setTo(mailContent.getRecipient());
			mailMessage.setText(mailContent.getMsgBody());
			mailMessage.setSubject(mailContent.getSubject());
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		catch (Exception e) {
			return "Error while Sending Mail";
		}
	}

	public Order cartMoveToOrder(String mail, String address) {
		User user = userRepo.findByEmail(mail);
		if (user == null) {
			throw new UserNotFound();
		}
		Cart cart = cartRepo.findByUserEmail(mail);
		if (cart == null) {
			throw new NoProductFound();
		}
		OrderDto order = new OrderDto();
		order.setEmail(mail);
		order.setAddress(address);
		List<OrderItemDto> OrderItems = new ArrayList<>();
		for (CartItem a : cart.getCartItems()) {
			OrderItemDto item = new OrderItemDto();
			Product product = a.getProduct();
			item.setProductid(product.getProductId());
			item.setQuantity(a.getQuantity());
			OrderItems.add(item);
		}
		order.setOrderItems(OrderItems);
		Order orderReturn= this.PlaceOrder(order);
		this.removeAllItemsFromCart(cart.getId());
		return orderReturn;
	}
	public void AlertMail(Product product)
	{
		MailSend mail = new MailSend();
	    mail.setRecipient("rakshitgobbi@gmail.com"); 
	    mail.setSubject("Low Stock Alert: " + product.getProductName());
	    
	    String message = "Dear Admin,\n\n"
	            + "The product \"" + product.getProductName() + "\" (ID: " + product.getProductId() + ") "
	            + "is running low on stock.\n"
	            + "Current available quantity: " + product.getQuantity() + "\n\n"
	            + "Please take necessary action.\n\n"
	            + "Regards,\n"
	            + "TCSkart Inventory System";

	    mail.setMsgBody(message);

	    this.received(mail) ;
	}
	public void removeAllItemsFromCart(Integer cartId) {

		List<CartItem> cartItems = cartItemRepo.findByCartId(cartId);

		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			product.setQuantity(product.getQuantity() + cartItem.getQuantity());
			productPepo.save(product);
		}

		cartItemRepo.deleteById(cartId);
	}
	public List<ProductSalesDTO> highestSellingProduct()
	{
		List<ProductSalesDTO>sellingProduct=itemRepo.findTopSellingProductsWithCount();	
		if(sellingProduct.size()==0)
		{
			throw new NoProductFound();
		}
		return sellingProduct;
	}
	public List<ProductSalesDTO> highestSellingProductTop5()
	{
		PageRequest pageRequest = PageRequest.of(0, 5);
		List<ProductSalesDTO> top5Products = itemRepo.findTopSellingProductsWithCountTop(pageRequest);
		
		if(top5Products.size()==0)
		{
			throw new NoProductFound();
		}
		return top5Products;
	}
	public List<ProductSalesByUser> highestSellingProductDistantUser()
	{
		PageRequest pageRequest = PageRequest.of(0, 5);
		List<ProductSalesByUser> top5Products = itemRepo.findTopSellingProductsByDistinctUsers(pageRequest);
		
		if(top5Products.size()==0)
		{
			throw new NoProductFound();
		}
		return top5Products;
	}
}
