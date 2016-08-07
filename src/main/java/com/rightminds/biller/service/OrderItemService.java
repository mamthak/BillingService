package com.rightminds.biller.service;

import com.rightminds.biller.entity.Item;
import com.rightminds.biller.entity.OrderItem;
import com.rightminds.biller.repository.OrderItemRepository;
import com.rightminds.biller.util.CastUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.rightminds.biller.util.CastUtil.getBigDecimal;

@Service
public class OrderItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderItemService.class);

    @Autowired
    private OrderItemRepository repository;

    @Autowired
    private ItemService itemService;

    public void save(OrderItem orderItem) {
        BigDecimal total = getTotal(orderItem);
        OrderItem updatedOrderItem = orderItem.withTotal(total);
        repository.save(updatedOrderItem);
    }

    public OrderItem getById(Integer id) {
        return repository.findById(id);
    }

    public List<OrderItem> getAll() {
        return (List<OrderItem>) repository.findAll();
    }

    private BigDecimal getTotal(OrderItem orderItem) {
        Item item = itemService.getById(orderItem.getItem().getId());
        LOGGER.debug("Fetched the item {}", item);
        BigDecimal total = item.getPrice().multiply(getBigDecimal(orderItem.getQuantity()));
        return total.subtract(orderItem.getDiscount());
    }
}
