package fontys.s3.andreipieleanu.domain.requestresponse.notifmsg;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationMessageRequest {
    private String id;
    private String from;
    private String to;
    private String text;
}
