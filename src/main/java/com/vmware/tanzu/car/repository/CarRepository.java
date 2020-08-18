package com.vmware.tanzu.car.repository;

import com.vmware.tanzu.car.domain.Car;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository responsible to manage cars in the database
 *
 * @author Diego Pereira da Rocha
 * @since JDK 14
 */
@Repository
public interface CarRepository extends CrudRepository<Car, UUID>, QuerydslPredicateExecutor<Car> {

}