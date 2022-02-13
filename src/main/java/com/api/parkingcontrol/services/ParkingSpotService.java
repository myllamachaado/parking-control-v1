package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ParkingSpotService {

   private final ParkingSpotRepository parkingSpotRepository;

   @Transactional
   public ParkingSpotModel save(ParkingSpotModel parkingSpotModel){
      return parkingSpotRepository.save(parkingSpotModel);
   }

   public Page<ParkingSpotModel> findAll(Pageable pageable){
      return parkingSpotRepository.findAll(pageable);
   }

   public Optional getById(String id){
      return parkingSpotRepository.findById(id);
   }

   @Transactional
   public void delete(String id){
      parkingSpotRepository.deleteById(id);
   }


   public boolean existsByLicensePlateCar(String licensePlateCar) {
      return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
   }

   public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
      return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
   }

   public boolean existsByApartmentAndBlock(String apartment, String block) {
      return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
   }
}
