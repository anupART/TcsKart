package com.tcskart.order_services.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcskart.order_services.bean.Order;

@Repository
public interface OrderRepo  extends CrudRepository<Order, Integer>{

}
