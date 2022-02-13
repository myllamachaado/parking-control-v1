package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dto.ParkingSpotDTO;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge=3600)
@RequestMapping("v1/parking-spot")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ParkingSpotController {

   private final ParkingSpotService parkingSpotService;

   @PostMapping("")
   public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO){

      if(parkingSpotService.existsByLicensePlateCar(parkingSpotDTO.getLicensePlateCar())){
         return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use!");
      }
      if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDTO.getParkingSpotNumber())){
         return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
      }
      if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDTO.getApartment(), parkingSpotDTO.getBlock())){
         return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
      }

      var parkingSpotModel = new ParkingSpotModel();
      BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
      parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
      parkingSpotModel.setId(UUID.randomUUID().toString());

      return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
   }

   @GetMapping("")
   public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpot(Pageable pageable){
      return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll(pageable));
   }

   @GetMapping("/{id}")
   public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") String id){
      Optional<ParkingSpotModel> parkingSpotModel = parkingSpotService.getById(id);
      if(!parkingSpotModel.isPresent()){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
      }
      return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModel.get());
   }

   @PutMapping("/{id}")
   public ResponseEntity<Object> editParkingSpot(@RequestBody @Valid ParkingSpotDTO parkingSpotDTO,
                                                 @PathVariable(value = "id") String id){
      Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.getById(id);
      if (!parkingSpotModelOptional.isPresent()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
      }
      var parkingSpotModel = new ParkingSpotModel();
      BeanUtils.copyProperties(parkingSpotDTO, parkingSpotModel);
      parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
      parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
      return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Object> getParkingSpot(@RequestBody @Valid String id){
      Optional parkingSpotModel = parkingSpotService.getById(id);
      if(!parkingSpotModel.isPresent()){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado");
      }

      parkingSpotService.delete(id);
      return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully");
   }


}
