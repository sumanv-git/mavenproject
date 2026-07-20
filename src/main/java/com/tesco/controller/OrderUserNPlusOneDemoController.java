package com.tesco.controller;

import com.tesco.service.demo.OrderUserNPlusOneDemoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderUserNPlusOneDemoController {

    private final OrderUserNPlusOneDemoService demoService;

    public OrderUserNPlusOneDemoController(OrderUserNPlusOneDemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/demo/n-plus-one/orders-users")
    public List<String> demoOrdersUsersNPlusOne() {

        //return demoService.nPlusOneOrdersToUsernames();
        //return demoService.fixedJoinFetchOrdersToUsernames();
        //return demoService.fixedEntityGraphOrdersToUsernames();
        return demoService.findAllOrderUserViews(); // To demonstrate custom query
    }
}