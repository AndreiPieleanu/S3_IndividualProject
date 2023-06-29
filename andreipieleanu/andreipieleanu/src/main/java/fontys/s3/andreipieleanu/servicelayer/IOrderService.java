package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.order.*;

public interface IOrderService {
    GetSimilarOrdersResponse getSimilarOrders(GetSimilarOrdersRequest request);
    CreateOrderResponse createOrder(CreateOrderRequest request);
    GetAllOrdersResponse getAllOrders();
    GetOrderResponse getOrder(GetOrderRequest request);
    GetOrdersOfUserResponse getOrdersOfUser(GetOrdersOfUserRequest request);
    UpdateOrderResponse updateOrder(UpdateOrderRequest request);
    GetOrderStatusesResponse getAllOrderStatuses();
    GetSalesForXPastMonthsResponse getSalesForThePastXMonths(GetSalesForXPastMonthsRequest request);
    GetTop5MostSoldProductsResponse getTop5MostSoldProductsForPastXMonths(GetTop5MostSoldProductsRequest request);
}
