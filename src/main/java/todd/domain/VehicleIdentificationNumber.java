package todd.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleIdentificationNumber {
    private String value;
}
