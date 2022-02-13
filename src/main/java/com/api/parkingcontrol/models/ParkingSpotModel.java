package com.api.parkingcontrol.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name="TB_PARKING_SPOT")
public class ParkingSpotModel implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(generator="system-uuid")
   @GenericGenerator(name="system-uuid", strategy = "uuid")
   @Column(unique = true)
   private String id;

   @Column(nullable = false, unique = true, length = 10)
   private String parkingSpotNumber;

   @Column(nullable = false, unique = true, length = 7)
   private String licensePlateCar;

   @Column(nullable = false, unique = false, length = 70)
   private String brandCar;

   @Column(nullable = false, unique = false, length = 70)
   private String modelCar;

   @Column(nullable = false, unique = false, length = 70)
   private String colorCar;

   @Column(nullable = false)
   private LocalDateTime registrationDate;

   @Column(nullable = false, length = 150)
   private String responsableName;

   @Column(nullable = false, length = 30)
   private String apartment;

   @Column(nullable = false, length = 30)
   private String block;

}
