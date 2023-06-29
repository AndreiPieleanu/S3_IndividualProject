package fontys.s3.andreipieleanu.servicelayer;

import fontys.s3.andreipieleanu.domain.AccessToken;

public interface IAccessTokenEncoder {
    String encode(AccessToken accessToken);
}
