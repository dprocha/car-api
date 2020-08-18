package com.vmware.tanzu.car.repository.predicates;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.vmware.tanzu.car.domain.QCar;
import com.vmware.tanzu.car.params.CarSearchParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * QueryDSLPredicate of Car
 *
 * @author Diego Pereira da Rocha
 * @since JDK 14
 */
@Validated
public class CarPredicates {

    private final QCar car = QCar.car;
    private final CarSearchParams carSearchParams;
    private final BooleanBuilder builder = new BooleanBuilder();

    public CarPredicates(@NotNull CarSearchParams carSearchParams) {
        this.carSearchParams = carSearchParams;
    }

    /**
     * Return Car Predicates
     *
     * @return
     */
    public Predicate getPredicates() {
        appendModel(carSearchParams.getModel());
        return builder;
    }

    /**
     * Add Predicate car domain
     *
     * @param name
     */
    private void appendModel(String name) {
        if (StringUtils.isNotBlank(name)) {
            builder.and(car.model.likeIgnoreCase(Expressions.asString("%").concat(name).concat("%")));
        }
    }

}

