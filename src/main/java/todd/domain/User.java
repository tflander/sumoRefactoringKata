package todd.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private UserGuid userGuid;
    private String accountKey;
    private String customerType;
}
