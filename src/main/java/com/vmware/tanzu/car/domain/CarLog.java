package com.vmware.tanzu.car.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * Transfer Object of Car for Redis Cache
 *
 * @since JDK 14
 */
@RedisHash(value = "carLog")
@Data
@Builder
public class CarLog implements Serializable {

    @Id
    private String carId;
    private String assembler;
    private String model;
    private Integer manufacturingYear;
    private Integer modelYear;
    private String dateTime;

}