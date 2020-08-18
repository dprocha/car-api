package com.vmware.tanzu.car.repository;

import com.vmware.tanzu.car.domain.CarLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository responsible to persist cars at Redis
 *
 * @author Diego Pereira da Rocha
 * @since JDK 14
 */
@Repository
public interface CarLogRepository extends CrudRepository<CarLog, String> {

}
