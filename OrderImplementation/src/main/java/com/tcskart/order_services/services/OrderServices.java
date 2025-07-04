package com.tcskart.order_services.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tcskart.order_services.bean.Order;
import com.tcskart.order_services.bean.OrderItem;
import com.tcskart.order_services.dao.OrderItemRepo;
import com.tcskart.order_services.dao.OrderRepo;
import com.tcskart.order_services.dto.CartItem;
import com.tcskart.order_services.dto.MailSend;
import com.tcskart.order_services.dto.OrderDto;
import com.tcskart.order_services.dto.OrderItemDto;
import com.tcskart.order_services.dto.ProductSalesByUser;
import com.tcskart.order_services.dto.ProductSalesDTO;
import com.tcskart.order_services.dto.ProductShare;
import com.tcskart.order_services.exception.NoOrder;
import com.tcskart.order_services.exception.NoProductFound;
import com.tcskart.order_services.exception.NotEnoughStock;
import com.tcskart.order_services.exception.ProductNotAvailable;
import com.tcskart.order_services.exception.ProductNotFound;

@Service
public class OrderServices {

	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	ProductDetailsByIdFeignClientImpl productService;

	@Autowired
	OrderItemRepo itemRepo;

	
	@Autowired
	ProductDetailsByIdFeignClientImpl sharerepo;
	
	@Autowired
	CartFeignClientImpl cartService;
	
	@Autowired
	private JavaMailSender javaMailSender;

	public Order PlaceOrder(OrderDto orderdto) {
		Order order = new Order();
		order.setUserEmail(orderdto.getEmail());
		String addressShow=orderdto.getAddress()+" Pincode : "+Integer.toString(orderdto.getPincode());
		order.setAddress(addressShow);
		order.setStatus("PENDING");
		LocalDate currentDate = LocalDate.now();
		order.setOrderDate(currentDate);

		List<OrderItem> items = new ArrayList<>();
		Double TotalPrice = 0.0;
		for (OrderItemDto dto : orderdto.getOrderItems()) {
			ProductShare product = sharerepo.getDetailsByProductId(dto.getProductid());
			if(product==null)	
			{
				  throw new ProductNotFound();
			}
			if(cartService.isProductNotAvailable(dto.getProductid(),orderdto.getPincode()))
			{
				 throw new ProductNotAvailable(product.getProductName());
			}
			if (product.getQuantity() < dto.getQuantity()) {
				this.AlertMail(product);
				throw new NotEnoughStock();
			}
		}
		ArrayList<String> ProductName = new ArrayList<>();
		for (OrderItemDto dto : orderdto.getOrderItems()) {
		  	ProductShare product = productService.getDetailsByProductId(dto.getProductid());  
			//System.out.println(availableRepo.existsByProduct_ProductIdAndPincode(dto.getProductid(),orderdto.getPincode()));
			product.setQuantity(product.getQuantity() - dto.getQuantity());
			if (product.getQuantity() < 5) {
                   this.AlertMail(product);
			}
			ProductName.add(product.getProductName());
			OrderItem item = new OrderItem();
			item.setOrder(order);
			item.setProductId(product.getProductId());
			item.setQuantity(dto.getQuantity());
			item.setPrice(product.getProductPrice() * dto.getQuantity());
			TotalPrice += item.getPrice();
			productService.updateQuantity(product.getProductId(), product.getQuantity());
			items.add(item);
		}

		order.setTotalAmount(TotalPrice);
		order.setOrderItems(items);
		MailSend mailContent = new MailSend();
		String recipient = orderdto.getEmail();
		mailContent.setRecipient(recipient);
		String subject = "Order Confirmation - Thank you for your purchase!";
		mailContent.setSubject(subject);
		StringBuilder msgBody = new StringBuilder();
		Order orderConfrim = orderRepo.save(order);
		msgBody.append("Dear ").append("").append(",\n\n")
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

	public Order cartMoveToOrder(String mail, String address,int pincode) {
		List<CartItem> cart = cartService.getAllItemItemsFromCart(mail);
		if (cart.size() == 0) {
			throw new NoProductFound();
		}
		OrderDto order = new OrderDto();
		order.setEmail(mail);
		order.setAddress(address);
		order.setPincode(pincode);
		List<OrderItemDto> OrderItems = new ArrayList<>();
		for (CartItem a : cart) {
			OrderItemDto item = new OrderItemDto();
			item.setProductid(a.getProductId());
			item.setQuantity(a.getQuantity());
			OrderItems.add(item);
		}
		order.setOrderItems(OrderItems);
		Order orderReturn= this.PlaceOrder(order);
		cartService.removeAllItemItemsFromCart(mail);
		return orderReturn;
	}
	public void AlertMail(ProductShare product)
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
