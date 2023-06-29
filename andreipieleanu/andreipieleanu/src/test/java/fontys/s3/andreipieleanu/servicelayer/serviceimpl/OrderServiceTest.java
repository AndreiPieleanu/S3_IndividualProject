package fontys.s3.andreipieleanu.servicelayer.serviceimpl;

import fontys.s3.andreipieleanu.datalayer.*;
import fontys.s3.andreipieleanu.datalayer.entities.*;
import fontys.s3.andreipieleanu.domain.*;
import fontys.s3.andreipieleanu.domain.requestresponse.order.*;
import fontys.s3.andreipieleanu.servicelayer.IOrderService;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.InvalidDateProvidedException;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.OrderNotFoundException;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.OrderStatusNotFoundException;
import fontys.s3.andreipieleanu.servicelayer.customexceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    private IOrderDal orderDal;
    private IShoppingCartDal shoppingCartDal;
    private IOrderItemDal orderItemDal;
    private IOrderStatusDal orderStatusDal;
    private IClothesDal clothesDal;
    private IOrderService orderService;
    private IAddressDal addressDal;
    private IUserDal userDal;
    private final UserEntity userEntity =
            UserEntity.builder().id(1).userRole(UserRoleEntity.builder().id(1).role(RoleEnum.CLIENT).build()).build();
    private final User user =
            User.builder().id(1).userRole(UserRole.builder().id(1).role(Role.CLIENT).build()).build();
    private final AddressEntity addressEntity = AddressEntity
            .builder()
            .id(1)
            .streetName("street")
            .streetNumber(1)
            .zipCode("zipcode")
            .city("city")
            .country("country")
            .phone("phone")
            .user(userEntity)
            .build();
    private final Address address = Address
            .builder()
            .id(1)
            .streetName("street")
            .streetNumber(1)
            .zipCode("zipcode")
            .city("city")
            .country("country")
            .phone("phone")
            .user(user)
            .build();
    private final List<OrderEntity> existentOrderEntities = List.of(
            OrderEntity.builder().id(1).orderItems(
                Map.of(1,
                        OrderItemEntity
                                .builder()
                                .id(1)
                                .amount(1)
                                .price(9.99)
                                .item(
                                        ClothesEntity.builder().id(1).subcategoryEntity(CategoryEntity.builder().id(1).name("Shirts").build()).isActive(true).sizesEntity(SizesEntity.S).build())
                                .build()
                        
                )
            ).customer(userEntity).status(OrderStatusEntity.builder().id(1).statusEnum(OrderStatusEnumEntity.NEW).build()).addressEntity(addressEntity).build()
    );
    private final Map<Integer, Order> existentOrders = Map.of(
            1, 
            Order.builder().id(1).orderItems(
                    Map.of(1,
                            OrderItem
                                    .builder()
                                    .id(1)
                                    .amount(1)
                                    .price(9.99)
                                    .item(
                                            Clothes.builder().id(1).subcategory(Category.builder().id(1).name("Shirts").build()).size(Sizes.S).isActive(true).build())
                                    .build()

                    )
            ).customer(user).address(address).status(OrderStatus.builder().id(1).orderStatusEnum(OrderStatusEnum.NEW).build()).build()
    );
    private final ShoppingCartEntity existentCart = ShoppingCartEntity
            .builder()
            .id(1)
            .user(userEntity)
            .cartItems(Map.of(1,
                    CartItemEntity.builder().id(1).amount(10).item(ClothesEntity.builder().id(1).subcategoryEntity(CategoryEntity.builder().id(1).name("Shirts").build()).sizesEntity(SizesEntity.S).amountInStock(100).measAmount(1).measUnit("unit").name("T-Shirt").description("A T-Shirt").price(9.99).build()).build()))
            .build();
    @BeforeEach
    public void setUp(){
        orderDal = mock(IOrderDal.class);
        shoppingCartDal = mock(IShoppingCartDal.class);
        orderItemDal = mock(IOrderItemDal.class);
        orderStatusDal = mock(IOrderStatusDal.class);
        clothesDal = mock(IClothesDal.class);
        orderService = mock(IOrderService.class);
        addressDal = mock(IAddressDal.class);
        userDal = mock(IUserDal.class);
        
        orderService = new OrderService(orderDal, shoppingCartDal,
                orderItemDal, orderStatusDal, clothesDal, userDal, addressDal);
    }
    @Test
    public void shouldGetAllOrdersWhenOrdersExist(){
        // Arrange 
        when(orderDal.findAll()).thenReturn(existentOrderEntities);
        // Act 
        GetAllOrdersResponse response = orderService.getAllOrders();
        // Assert 
        verify(orderDal).findAll();
        assertNotNull(response);
        assertEquals(existentOrders, response.getOrders());
    }
    @Test
    public void getOrderShouldReturnIt_WhenOrderExists(){
        // Arrange 
        Integer existentId = 1;
        GetOrderRequest request = new GetOrderRequest(existentId);
        when(orderDal.findById(existentId)).thenReturn(Optional.of(existentOrderEntities.get(0)));
        // Act 
        GetOrderResponse response = orderService.getOrder(request);
        // Assert 
        verify(orderDal).findById(existentId);
        assertNotNull(response);
        assertNotNull(response.getFoundOrder());
        assertEquals(existentOrderEntities.get(0).getId(), response.getFoundOrder().getId());
    }
    @Test
    public void getOrderShouldThrowException_WhenOrderDoesNotExist(){
        // Arrange 
        Integer existentId = 1;
        GetOrderRequest request = new GetOrderRequest(existentId);
        when(orderDal.findById(existentId)).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"ORDER COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void getAllOrdersOfUserShouldGetList_WhenOrdersExist(){
        // Arrange 
        GetOrdersOfUserRequest request =
                new GetOrdersOfUserRequest(userEntity.getId());
        when(orderDal.getAllOrdersOfUser(userEntity.getId())).thenReturn(existentOrderEntities);
        // Act 
        GetOrdersOfUserResponse response =
                orderService.getOrdersOfUser(request);
        // Assert 
        assertNotNull(response);
        assertNotNull(response.getOrders());
        assertEquals(existentOrderEntities.get(0).getId(),
                response.getOrders().get(1).getId());
    }
    @Test
    public void updateOrderStatusShouldChange_WhenOrderExists(){
        // Arrange 
        OrderStatusEnum newStatus = OrderStatusEnum.PENDING;
        Integer orderId = existentOrderEntities.get(0).getId();
        UpdateOrderRequest request = new UpdateOrderRequest(newStatus, orderId);
        when(orderDal.updateOrderStatus(newStatus.getValue(), orderId)).thenReturn(1);
        // Act 
        UpdateOrderResponse response = orderService.updateOrder(request);
        // Assert 
        verify(orderDal).updateOrderStatus(newStatus.getValue(), orderId);
        assertNotNull(response);
        assertEquals(1, response.getUpdatedRows());
    }
    @Test
    public void createOrderShouldWork_WhenDataIsProvided(){
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest(1, 1, 1);
        OrderEntity createdOrderEntity = OrderEntity.builder().id(1).build();
        when(orderStatusDal.findById(1)).thenReturn(Optional.of(OrderStatusEntity.builder().id(1).statusEnum(OrderStatusEnumEntity.NEW).build()));
        when(orderDal.save(any(OrderEntity.class))).thenReturn(createdOrderEntity);
        when(addressDal.findById(addressEntity.getId())).thenReturn(Optional.of(addressEntity));
        when(shoppingCartDal.getShoppingCartBasedOnUserId(userEntity.getId())).thenReturn(Optional.of(existentCart));
        when(userDal.findById(1)).thenReturn(Optional.of(UserEntity.builder().id(1).build()));
        // Act 
        CreateOrderResponse response = orderService.createOrder(request);
        // Assert 
        verify(orderStatusDal).findById(1);
        verify(orderDal).save(any(OrderEntity.class));
        verify(orderItemDal).save(any(OrderItemEntity.class));
        verify(clothesDal).updateClothes(any(ClothesEntity.class));
        verify(shoppingCartDal).emptyCartWithId(1);
        assertNotNull(response);
        assertEquals(1, response.getCreatedId());
    }
    @Test
    public void createOrderShouldThrowException_WhenOrderStatusNewIsNotFound(){
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest(1, 1, 1);
        when(orderStatusDal.findById(1)).thenReturn(Optional.empty());
        when(shoppingCartDal.getShoppingCartBasedOnUserId(userEntity.getId())).thenReturn(Optional.of(existentCart));
        when(userDal.findById(1)).thenReturn(Optional.of(UserEntity.builder().id(1).build()));
        // Act 
        Throwable thrown = assertThrows(OrderStatusNotFoundException.class,
                () -> orderService.createOrder(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"ORDER STATUS COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void createOrderShouldThrowException_WhenUserIsNotFound(){
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest(1, 1, 1);
        when(userDal.findById(1)).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(UserNotFoundException.class,
                () -> orderService.createOrder(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"USER COULD NOT BE FOUND\"",
                thrown.getMessage());
    }
    
    @Test
    public void getTop5MostSoldItemsShouldRetrieveOnly3WhenOnly3WereSold(){
        // Arrange
        GetTop5MostSoldProductsRequest request = new GetTop5MostSoldProductsRequest("10.03.2001", "12.03.2001");
        when(orderDal.getTop5MostSoldProductsForPastXMonths(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(
                new Object[]{ ClothesEntity.builder().id(1).subcategoryEntity(CategoryEntity.builder().id(1).name("Shirts 1").build()).sizesEntity(SizesEntity.S).build(), 1L},
                new Object[]{ ClothesEntity.builder().id(2).subcategoryEntity(CategoryEntity.builder().id(2).name("Shirts 2").build()).sizesEntity(SizesEntity.S).build(), 2L},
                new Object[]{ ClothesEntity.builder().id(3).subcategoryEntity(CategoryEntity.builder().id(3).name("Shirts 3").build()).sizesEntity(SizesEntity.S).build(), 3L}
        ));
        Map<ClothesEntity, Long> expected = Map.of(
                ClothesEntity.builder().id(1).subcategoryEntity(CategoryEntity.builder().id(1).name("Shirts 1").build()).sizesEntity(SizesEntity.S).build(), 1L,
                ClothesEntity.builder().id(2).subcategoryEntity(CategoryEntity.builder().id(2).name("Shirts 2").build()).sizesEntity(SizesEntity.S).build(), 2L,
                ClothesEntity.builder().id(3).subcategoryEntity(CategoryEntity.builder().id(3).name("Shirts 3").build()).sizesEntity(SizesEntity.S).build(), 3L
        );
        // Act 
        GetTop5MostSoldProductsResponse response =
                orderService.getTop5MostSoldProductsForPastXMonths(request);
        // Assert 
        assertNotNull(response);
        assertEquals(expected, response.getSoldProducts());
    }
    
    @Test
    public void getTop5MostSoldItemsShouldRetrieveOnly5WhenMoreWereSold(){
        // Arrange
        GetTop5MostSoldProductsRequest request = new GetTop5MostSoldProductsRequest("10.03.2001", "12.03.2001");
        when(orderDal.getTop5MostSoldProductsForPastXMonths(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of(
                new Object[]{ ClothesEntity.builder().id(1).subcategoryEntity(CategoryEntity.builder().id(1).name("Shirts 1").build()).sizesEntity(SizesEntity.S).build(), 1L},
                new Object[]{ ClothesEntity.builder().id(2).subcategoryEntity(CategoryEntity.builder().id(2).name("Shirts 2").build()).sizesEntity(SizesEntity.S).build(), 2L},
                new Object[]{ ClothesEntity.builder().id(3).subcategoryEntity(CategoryEntity.builder().id(3).name("Shirts 3").build()).sizesEntity(SizesEntity.S).build(), 3L},
                new Object[]{ ClothesEntity.builder().id(4).subcategoryEntity(CategoryEntity.builder().id(4).name("Shirts 4").build()).sizesEntity(SizesEntity.S).build(), 4L},
                new Object[]{ ClothesEntity.builder().id(5).subcategoryEntity(CategoryEntity.builder().id(5).name("Shirts 5").build()).sizesEntity(SizesEntity.S).build(), 5L},
                new Object[]{ ClothesEntity.builder().id(6).subcategoryEntity(CategoryEntity.builder().id(6).name("Shirts 6").build()).sizesEntity(SizesEntity.S).build(), 6L}
        ));
        Map<ClothesEntity, Long> expected = Map.of(
                ClothesEntity.builder().id(1).subcategoryEntity(CategoryEntity.builder().id(1).name("Shirts 1").build()).sizesEntity(SizesEntity.S).build(), 1L,
                ClothesEntity.builder().id(2).subcategoryEntity(CategoryEntity.builder().id(2).name("Shirts 2").build()).sizesEntity(SizesEntity.S).build(), 2L,
                ClothesEntity.builder().id(3).subcategoryEntity(CategoryEntity.builder().id(3).name("Shirts 3").build()).sizesEntity(SizesEntity.S).build(), 3L,
                ClothesEntity.builder().id(4).subcategoryEntity(CategoryEntity.builder().id(4).name("Shirts 4").build()).sizesEntity(SizesEntity.S).build(), 4L,
                ClothesEntity.builder().id(5).subcategoryEntity(CategoryEntity.builder().id(5).name("Shirts 5").build()).sizesEntity(SizesEntity.S).build(), 5L
        );
        // Act 
        GetTop5MostSoldProductsResponse response =
                orderService.getTop5MostSoldProductsForPastXMonths(request);
        // Assert 
        assertNotNull(response);
        assertEquals(expected, response.getSoldProducts());
    }

    @Test
    public void getTop5MostSoldItemsShouldThrowInvalidDateProvidedExceptionWhenADateIsIncorrect(){
        // Arrange
        GetTop5MostSoldProductsRequest request = new GetTop5MostSoldProductsRequest("10.22.2001", "12.03.2001");
        // Act 
        Throwable thrown = assertThrows(InvalidDateProvidedException.class,
                () -> orderService.getTop5MostSoldProductsForPastXMonths(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("400 BAD_REQUEST \"DATE MUST HAVE FORMAT: dd.MM.yyyy\"", thrown.getMessage());
    }
    
    @Test
    public void getTop5MostSoldItemsShouldThrowInvalidDateProvidedExceptionWhenWhenADateHasWrongFormat(){
        // Arrange
        GetTop5MostSoldProductsRequest request =
                new GetTop5MostSoldProductsRequest("10/03/2001", "12.03.2001");
        // Act 
        Throwable thrown = assertThrows(InvalidDateProvidedException.class,
                () -> orderService.getTop5MostSoldProductsForPastXMonths(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("400 BAD_REQUEST \"DATE MUST HAVE FORMAT: dd.MM.yyyy\"", thrown.getMessage());
    }
    @Test
    public void getSalesShouldRetrieveGraph_WhenItemWereSold(){
        // Arrange
        GetSalesForXPastMonthsRequest request = new GetSalesForXPastMonthsRequest("10.03.2001", "12.03.2001");
        when(orderDal.calculateSalesForPastXMonths(any(LocalDateTime.class),
                any(LocalDateTime.class))).thenReturn(List.of(
                new Object[]{ 2003, 10, 100.0},
                new Object[]{ 2003, 11, 200.0},
                new Object[]{ 2003, 12, 300.0}
        ));
        // Act 
        GetSalesForXPastMonthsResponse response =
                orderService.getSalesForThePastXMonths(request);
        // Assert 
        verify(orderDal).calculateSalesForPastXMonths(any(LocalDateTime.class), any(LocalDateTime.class));
        assertNotNull(response);
        assertEquals(3, response.getRetrievedSales().size());
    }
    
    @Test
    public void getSalesShouldThrowInvalidDateProvidedExceptionWhenADateIsIncorrect(){
        // Arrange
        GetSalesForXPastMonthsRequest request = new GetSalesForXPastMonthsRequest("10.22.2001", "12.03.2001");
        // Act 
        Throwable thrown = assertThrows(InvalidDateProvidedException.class,
                () -> orderService.getSalesForThePastXMonths(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("400 BAD_REQUEST \"DATE MUST HAVE FORMAT: dd.MM.yyyy\"", thrown.getMessage());
    }

    @Test
    public void getSalesShouldThrowInvalidDateProvidedExceptionWhenWhenADateHasWrongFormat(){
        // Arrange
        GetSalesForXPastMonthsRequest request =
                new GetSalesForXPastMonthsRequest("10/03/2001", "12.03.2001");
        // Act 
        Throwable thrown = assertThrows(InvalidDateProvidedException.class,
                () -> orderService.getSalesForThePastXMonths(request));
        // Assert 
        assertNotNull(thrown);
        assertEquals("400 BAD_REQUEST \"DATE MUST HAVE FORMAT: dd.MM.yyyy\"", thrown.getMessage());
    }
    @Test
    public void getAllOrderStatusesShouldReturnList_WhenStatusesExist(){
        // Arrange 
        when(orderStatusDal.findAll()).thenReturn(List.of(OrderStatusEntity.builder().id(1).statusEnum(OrderStatusEnumEntity.NEW).build()));
        List<OrderStatus> expected =
                List.of(OrderStatus.builder().id(1).orderStatusEnum(OrderStatusEnum.NEW).build());
        // Act 
        GetOrderStatusesResponse response = orderService.getAllOrderStatuses();
        // Assert 
        verify(orderStatusDal).findAll();
        assertNotNull(response);
        assertEquals(expected, response.getStatuses());
    }
    @Test
    public void getAllSimilarOrdersShouldReturnList_WhenTheyExist(){
        // Arrange 
        GetSimilarOrdersRequest request = new GetSimilarOrdersRequest(1);
        List<OrderEntity> orders = List.of(
                OrderEntity.builder().customer(UserEntity.builder().id(1).userRole(UserRoleEntity.builder().id(1).build()).build()).addressEntity(AddressEntity.builder().streetNumber(0).user(UserEntity.builder().id(1).build()).build()).status(OrderStatusEntity.builder().id(1).statusEnum(OrderStatusEnumEntity.NEW).build()).orderItems(new HashMap<>()).build()
        );
        List<Order> expected = List.of(
                Order.builder().customer(User.builder().id(1).userRole(UserRole.builder().id(1).build()).build()).address(Address.builder().streetNumber(0).user(User.builder().id(1).build()).build()).orderItems(new HashMap<>()).status(OrderStatus.builder().id(1).orderStatusEnum(OrderStatusEnum.NEW).build()).build()
        );
        when(orderDal.findAllBySimilarOrderId(request.getOrderId())).thenReturn(orders);
        // Act 
        GetSimilarOrdersResponse response = orderService.getSimilarOrders(request);
        // Assert 
        verify(orderDal).findAllBySimilarOrderId(request.getOrderId());
        assertNotNull(response);
        assertEquals(expected, response.getOrders());
    }
}