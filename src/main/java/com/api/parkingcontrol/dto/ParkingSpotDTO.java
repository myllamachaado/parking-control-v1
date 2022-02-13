package com.api.parkingcontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotDTO {

   @NotBlank
   private String parkingSpotNumber;
   @NotBlank
   @Size(max = 7)
   private String licensePlateCar;
   @NotBlank
   private String brandCar;
   @NotBlank
   private String modelCar;
   @NotBlank
   private String colorCar;
   @NotBlank
   private String responsableName;
   @NotBlank
   private String apartment;
   @NotBlank
   private String block;

}
