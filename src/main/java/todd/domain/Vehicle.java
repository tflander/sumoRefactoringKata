package todd.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Vehicle {
    private  VehicleIdentificationNumber vin;
}
