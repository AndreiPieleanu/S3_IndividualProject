package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.*;
import fontys.s3.andreipieleanu.datalayer.entities.*;
import fontys.s3.andreipieleanu.domain.Order;
import fontys.s3.andreipieleanu.domain.OrderStatus;
import fontys.s3.andreipieleanu.domain.requestresponse.order.*;
import fontys.s3.andreipieleanu.servicelayer.IOrderService;
import fontys.s3.andreipieleanu.servicelayer.converters.OrderConverter;
import fontys.s3.andreipieleanu.servicelayer.converters.OrderStatusConverter;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService {
    private final IOrderDal orderDal;
    private final IShoppingCartDal shoppingCartDal;
    private final IOrderItemDal orderItemDal;
    private final IOrderStatusDal orderStatusDal;
    private final IClothesDal clothesDal;
    private final IUserDal userDal;
    private final IAddressDal addressDal;

    @Override
    public GetSimilarOrdersResponse getSimilarOrders(GetSimilarOrdersRequest request) {
        List<OrderEntity> foundOrders = orderDal.findAllBySimilarOrderId(request.getOrderId());
        List<Order> convertedOrders = foundOrders.stream().map(OrderConverter::convert).toList();
        return new GetSimilarOrdersResponse(convertedOrders);
    }

    @Transactional
    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        UserEntity foundUser =
                userDal.findById(request.getCustomerId()).orElseThrow(UserNotFoundException::new);
        ShoppingCartEntity shoppingCartEntity =
                shoppingCartDal.getShoppingCartBasedOnUserId(request.getCustomerId()).orElseThrow(CartItemNotFoundException::new);
        Map<Integer, OrderItemEntity> itemEntities = new HashMap<>();
        shoppingCartEntity.getCartItems().forEach((k, v) -> {
            OrderItemEntity orderItemEntity =
                    OrderItemEntity
                            .builder()
                            .item(v.getItem())
                            .price(v.getItem().getPrice())
                            .amount(v.getAmount())
                            .build();
            itemEntities.put(k, orderItemEntity);
        });

        OrderStatusEntity foundStatus =
                orderStatusDal.findById(OrderStatusEnumEntity.NEW.getValue()).orElseThrow(OrderStatusNotFoundException::new);

        AddressEntity foundAddress =
                addressDal.findById(request.getAddressId()).orElseThrow(AddressNotFoundException::new);

        OrderEntity orderToCreate = OrderEntity
                .builder()
                .status(foundStatus)
                .createdDate(LocalDateTime.now())
                .orderItems(itemEntities)
                .customer(foundUser)
                .addressEntity(foundAddress)
                .orderItems(new HashMap<>())
                .build();
        orderToCreate = orderDal.save(orderToCreate);

        OrderEntity finalOrderToCreate = orderToCreate;
        itemEntities.forEach((k, v) -> {
            Integer clothesAmountInStock = v.getItem().getAmountInStock();
            Integer newAmountInStock = clothesAmountInStock - v.getAmount();
            v.setOrderEntity(finalOrderToCreate); // Set the orderEntity to finalOrderToCreate
            v.getItem().setAmountInStock(newAmountInStock);
            orderItemDal.save(v);
            clothesDal.updateClothes(v.getItem());
        });

        shoppingCartDal.emptyCartWithId(request.getCartId());

        return new CreateOrderResponse(finalOrderToCreate.getId());
    }
    @Override
    public GetAllOrdersResponse getAllOrders() {
        List<OrderEntity> orders = orderDal.findAll();
        
        Map<Integer, Order> convertedOrders = new HashMap<>();
        
        orders.stream().map(OrderConverter::convert).forEach(order -> convertedOrders.put(order.getId(), order));
        
        return new GetAllOrdersResponse(convertedOrders);
    }

    @Override
    public GetOrderResponse getOrder(GetOrderRequest request) {
        OrderEntity foundOrder = orderDal.findById(request.getOrderId()).orElseThrow(OrderNotFoundException::new);
        Order convertedOrder = OrderConverter.convert(foundOrder);
        return new GetOrderResponse(convertedOrder);
    }

    @Override
    public GetOrdersOfUserResponse getOrdersOfUser(GetOrdersOfUserRequest request) {
        List<OrderEntity> foundOrders = orderDal.getAllOrdersOfUser(request.getUserId());
        
        Map<Integer, Order> convertedOrders = new HashMap<>();
        
        foundOrders.stream().map(OrderConverter::convert).forEach(order -> convertedOrders.put(order.getId(), order));

        return new GetOrdersOfUserResponse(convertedOrders);
    }

    @Override
    @Transactional
    public UpdateOrderResponse updateOrder(UpdateOrderRequest request) {
        Integer updatedRows =
                orderDal.updateOrderStatus(
                        request.getNewStatus().getValue(), 
                        request.getOrderId()
                );
        return new UpdateOrderResponse(updatedRows);
    }

    @Override
    public GetOrderStatusesResponse getAllOrderStatuses() {
        List<OrderStatusEntity> foundOrders = orderStatusDal.findAll();
        List<OrderStatus> convertedOrders =
                foundOrders.stream().map(OrderStatusConverter::convert).toList();
        return new GetOrderStatusesResponse(convertedOrders);
    }
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    @Override
    public GetSalesForXPastMonthsResponse getSalesForThePastXMonths(GetSalesForXPastMonthsRequest request) {
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(request.getStartDate(), dtf);
            endDate = LocalDate.parse(request.getEndDate(), dtf);
        }catch (DateTimeParseException ex) {
            throw new InvalidDateProvidedException();
        }
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
        
        List<Object[]> results = orderDal.calculateSalesForPastXMonths(startDateTime, endDateTime);

        Map<String, Double> sales = results.stream()
                .collect(Collectors.toMap(
                        obj -> YearMonth.of((int) obj[0], (int) obj[1]).toString(),
                        obj -> (Double) obj[2],
                        (existing, replacement) -> existing));
        return new GetSalesForXPastMonthsResponse(sales);
    }

    @Override
    public GetTop5MostSoldProductsResponse getTop5MostSoldProductsForPastXMonths(GetTop5MostSoldProductsRequest request) {
        LocalDate startDate;
        LocalDate endDate;
        try {
            startDate = LocalDate.parse(request.getStartDate(), dtf);
            endDate = LocalDate.parse(request.getEndDate(), dtf);
        }catch (DateTimeParseException ex) {
            throw new InvalidDateProvidedException();
        }

        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);

        List<Object[]> results = orderDal.getTop5MostSoldProductsForPastXMonths(startDateTime, endDateTime);
        Map<ClothesEntity, Long> products = results.stream()
                .limit(5)
                .collect(Collectors.toMap(
                        obj -> (ClothesEntity) obj[0],
                        obj -> (Long) obj[1],
                        (existing, replacement) -> existing));

        return new GetTop5MostSoldProductsResponse(products);
    }
}
