package orderservice.api.order_service.controller;

import lombok.RequiredArgsConstructor;
import orderservice.api.order_service.dto.OrderRequest;
import orderservice.api.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Товар успешно добавлен";
    }
}
