package com.vmware.tanzu.car.service;

import com.vmware.tanzu.car.domain.Car;
import com.vmware.tanzu.car.domain.CarLog;
import com.vmware.tanzu.car.exception.CarNotFoundException;
import com.vmware.tanzu.car.params.CarSearchParams;
import com.vmware.tanzu.car.repository.CarLogRepository;
import com.vmware.tanzu.car.repository.CarRepository;
import com.vmware.tanzu.car.repository.predicates.CarPredicates;
import com.vmware.tanzu.car.resource.CarResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of Car Service
 *
 * @author Diego Pereira da Rocha
 * @since JDK 14
 */
@Service
@Slf4j
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private CarLogRepository carLogRepository;

    public CarServiceImpl(CarRepository carRepository, CarLogRepository carLogRepository) {
        this.carRepository = carRepository;
        this.carLogRepository = carLogRepository;
    }

    /**
     * Convert entity to Mode (Domain->Resource)
     */
    private Function<Car, CarResource> mapEntityToResource = car ->
            CarResource.builder()
                    .id(car.getId())
                    .assembler(car.getAssembler())
                    .model(car.getModel())
                    .manufacturingYear(car.getManufacturingYear())
                    .modelYear(car.getModelYear())
                    .build();
    /**
     * Convert Resource to Entity (Resource-> Entity)
     */
    private Function<CarResource, Car> mapResourceToEntity = carResource ->
            Car.builder()
                    .id(carResource.getId())
                    .assembler(carResource.getAssembler())
                    .model(carResource.getModel())
                    .manufacturingYear(carResource.getManufacturingYear())
                    .modelYear(carResource.getModelYear())
                    .build();

    /**
     * @see CarService#create(CarResource)
     */
    @Override
    @Transactional
    public CarResource create(CarResource carResource) {
        return createOrUpdate(carResource);
    }

    /**
     * @param carId
     * @see CarService#read(UUID)
     */
    @Override
    @Transactional(readOnly = true)
    public CarResource read(UUID carId) {
        checkCarExists(carId);
        return mapEntityToResource.apply(carRepository.findById(carId).get());
    }

    /**
     * @see CarService#update(CarResource)
     */
    @Override
    @Transactional
    public void update(CarResource carResource) {
        checkCarExists(carResource.getId());
        createOrUpdate(carResource);
    }

    /**
     * @param carId
     * @see CarService#delete(UUID)
     */
    @Override
    @Transactional
    public void delete(UUID carId) {
        checkCarExists(carId);
        carRepository.deleteById(carId);
        carLogRepository.deleteById(carId.toString());
    }

    /**
     * @see CarService#search(CarSearchParams)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarResource> search(CarSearchParams carSearchParams) {
        Page<Car> page = carRepository.findAll(new CarPredicates(carSearchParams).getPredicates(), carSearchParams.getPageable());
        List<CarResource> carResources = page.getContent()
                .stream()
                .map(mapEntityToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(carResources, carSearchParams.getPageable(), page.getTotalElements());
    }

    /**
     * @see CarService#getAllCarLog()
     */
    @Override
    public Iterable<CarLog> getAllCarLog() {
        return carLogRepository.findAll();
    }

    /**
     * Check if the Car with id provided exist in the database
     *
     * @param idCar
     */
    private void checkCarExists(UUID idCar) {
        Boolean exists = carRepository.existsById(idCar);
        if (!exists) {
            throw new CarNotFoundException();
        }
    }

    /**
     * Create Or Update a Car
     *
     * @param carResource
     * @return
     */
    private CarResource createOrUpdate(CarResource carResource) {
        Car car = mapResourceToEntity.apply(carResource);
        car = carRepository.save(car);
        log.debug("Saving Car {} ", car.getId());
        carLogRepository.save(CarLog.builder()
                .carId(car.getId().toString())
                .assembler(car.getAssembler())
                .model(car.getModel())
                .manufacturingYear(car.getManufacturingYear())
                .modelYear(car.getModelYear())
                .dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .build());
        log.debug("Saving Car on Redis {} ", car.getId());
        return mapEntityToResource.apply(car);
    }

}