package fontys.s3.andreipieleanu.controller;

import fontys.s3.andreipieleanu.configuration.security.isauthenticated.IsAuthenticated;
import fontys.s3.andreipieleanu.domain.requestresponse.order.*;
import fontys.s3.andreipieleanu.servicelayer.IOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @GetMapping("{orderId}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable(name =
            "orderId") Integer orderId){
        GetOrderRequest request = new GetOrderRequest(orderId);
        return ResponseEntity.ok(orderService.getOrder(request));
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @GetMapping("{orderId}/similar")
    public ResponseEntity<GetSimilarOrdersResponse> getSimilarOrders(@PathVariable(name =
            "orderId") Integer orderId){
        GetSimilarOrdersRequest request = new GetSimilarOrdersRequest(orderId);
        return ResponseEntity.ok(orderService.getSimilarOrders(request));
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetOrdersOfUserResponse> getOrdersOfUser(@PathVariable(name = "userId") Integer userId){
        GetOrdersOfUserRequest request = new GetOrdersOfUserRequest(userId);
        return ResponseEntity.ok(orderService.getOrdersOfUser(request));
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @GetMapping("/all")
    public ResponseEntity<GetAllOrdersResponse> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @GetMapping("/statuses")
    public ResponseEntity<GetOrderStatusesResponse> getAllOrdersStatuses(){
        return ResponseEntity.ok(orderService.getAllOrderStatuses());
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @PutMapping
    public ResponseEntity<UpdateOrderResponse> updateOrder(@RequestBody @Valid UpdateOrderRequest request){
        return ResponseEntity.ok(orderService.updateOrder(request));
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_CLIENT"})
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest request){
        return ResponseEntity.ok(orderService.createOrder(request));
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @GetMapping("/sales")
    public ResponseEntity<GetSalesForXPastMonthsResponse> getSalesForXPastMonths(@RequestBody @Valid GetSalesForXPastMonthsRequest request){
        return ResponseEntity.ok(orderService.getSalesForThePastXMonths(request));
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_WORKER"})
    @GetMapping("/top5")
    public ResponseEntity<GetTop5MostSoldProductsResponse> getTop5MostSoldProductsForPastXMonths(@RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate){
        GetTop5MostSoldProductsRequest request =
                new GetTop5MostSoldProductsRequest(startDate, endDate);
        return ResponseEntity.ok(orderService.getTop5MostSoldProductsForPastXMonths(request));
    }
}
