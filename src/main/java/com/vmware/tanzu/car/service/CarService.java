package com.vmware.tanzu.car.service;

import com.vmware.tanzu.car.domain.CarLog;
import com.vmware.tanzu.car.params.CarSearchParams;
import com.vmware.tanzu.car.resource.CarResource;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Service of Car
 *
 * @author Diego Pereira da Rocha
 * @since JDK 14
 */
@Validated
public interface CarService {

    /**
     * Create a car
     *
     * @param carResource
     */
    CarResource create(@NotNull @Valid CarResource carResource);

    /**
     * Get a car by Id
     *
     * @param carId
     * @return
     */
    CarResource read(@NotNull UUID carId);

    /**
     * Update a car by Id
     *
     * @param carResource
     */
    void update(@NotNull @Valid CarResource carResource);

    /**
     * Delete a car by Id
     *
     * @param carId
     */
    void delete(@NotNull UUID carId);

    /**
     * Search the cars based in the car parameters
     *
     * @param carSearchParams
     * @return
     */
    Page<CarResource> search(@NotNull CarSearchParams carSearchParams);

    /**
     * Return all Car Log on Redis
     *
     * @return
     */
    Iterable<CarLog> getAllCarLog();

}