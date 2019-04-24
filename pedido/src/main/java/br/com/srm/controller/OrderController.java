package br.com.srm.controller;

import br.com.srm.model.Order;
import br.com.srm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public Order create(@RequestBody Order order) {
        return orderService.create(order);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Order> findByClient(@RequestParam("cpf") String cpf) {
        return orderService.findByClient(cpf);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order findById(@PathVariable("id") String id) {
        return orderService.findById(id);
    }

    @RequestMapping(value = "/{id}/finish", method = RequestMethod.PUT)
    public Order finish(@PathVariable("id") String id) {
        return orderService.finish(id);
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.PUT)
    public Order cancel(@PathVariable("id") String id) {
        return orderService.cancel(id);
    }

}
