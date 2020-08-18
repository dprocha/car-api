package com.vmware.tanzu.car.params;

import lombok.Data;
import org.springframework.data.domain.Pageable;

/**
 * Class Responsible for store QueryParams of REST API
 *
 * @author Diego Pereira da Rocha
 * @since JDK 14
 */
@Data
public class CarSearchParams {

    private String model;
    private Pageable pageable;

}