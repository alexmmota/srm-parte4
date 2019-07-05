package br.com.srm.service;

import br.com.srm.client.EstoqueClient;
import br.com.srm.exception.BusinessServiceException;
import br.com.srm.model.Order;
import br.com.srm.model.OrderItem;
import br.com.srm.client.dto.Product;
import br.com.srm.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EstoqueClient estoqueClient;

    @HystrixCommand
    public Order create(Order order) {
        logger.info("m=create, order={}", order);
        order.setCreateDate(new Date());
        order.setStatus(Order.Status.CREATED);
        return orderRepository.save(order);
    }

    @HystrixCommand
    public Order findById(String id) {
        return getOrderById(id);
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")},
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "3")
            },
            fallbackMethod = "fallBackFindByClient")
    public List<Order> findByClient(String cpf) {
        sleep();
        return orderRepository.findByClient_CpfOrderByCreateDateAsc(cpf);
    }

    public List<Order> fallBackFindByClient(String cpf) {
        return Collections.EMPTY_LIST;
    }


    public Order finish(String id) {
        logger.info("m=finish, id={}", id);
        Order order = getOrderById(id);
        validateItens(order);
        order.setFinishDate(new Date());
        order.setStatus(Order.Status.FINISHED);
        updateProductAmount(order);
        return orderRepository.save(order);
    }

    public Order cancel(String id) {
        logger.info("m=cancel, id={}", id);
        Order order = getOrderById(id);
        order.setFinishDate(new Date());
        order.setStatus(Order.Status.CANCELED);
        return orderRepository.save(order);
    }

    private void validateItens(Order order) {
        for (OrderItem item : order.getItens()) {
            Product product = findProductByBarCode(item);
            if (product.getAmount() < item.getAmount())
                throw new BusinessServiceException("Quantidade de produto insuficiente no estoque");
        }
    }

    @HystrixCommand(threadPoolKey = "productByBarCodeThreadPool",
            threadPoolProperties = {
                @HystrixProperty(name = "coreSize",value="30"),
                @HystrixProperty(name="maxQueueSize", value="10")})
    private Product findProductByBarCode(OrderItem item) {
        sleep();
        return estoqueClient.findByIsbn(1l, item.getProduct());
    }

    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateProductAmount(Order order) {
        for (OrderItem item : order.getItens()) {
            subtractProductAmount(item);
        }
    }

    @HystrixCommand
    private void subtractProductAmount(OrderItem item) {
        estoqueClient.subtractAmount(1l, item.getProduct(), item.getAmount());
    }

    private Order getOrderById(String id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent())
            throw new BusinessServiceException("Pedido não encontrado");
        return optionalOrder.get();
    }

}
