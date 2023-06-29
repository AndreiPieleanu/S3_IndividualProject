package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.requestresponse.item.EditItemRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.item.EditItemResponse;
import fontys.s3.andreipieleanu.domain.requestresponse.item.RemoveItemFromCartRequest;
import fontys.s3.andreipieleanu.domain.requestresponse.item.RemoveItemFromCartResponse;

public interface ICartItemService {
    EditItemResponse editItem(EditItemRequest request);
    RemoveItemFromCartResponse removeItem(RemoveItemFromCartRequest request);
}
