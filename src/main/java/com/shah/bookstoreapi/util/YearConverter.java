package com.shah.bookstoreapi.util;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
@Slf4j
public class YearConverter implements AttributeConverter<Year, Short> {


    /**
     *  java.time.Year to attribute with Hibernate in DB
     * @param attribute  the entity attribute value to be converted
     * @return
     */
    @Override
    public Short convertToDatabaseColumn(Year attribute) {
        short year = (short) attribute.getValue();
        log.info("Convert Year [{}] to short [{}]",attribute,year);
        return year;
    }

    @Override
    public Year convertToEntityAttribute(Short dbValue) {
        Year year = Year.of(dbValue);
        log.info("Convert Short [{}] to Year [{}]",dbValue,year);
        return year;
    }
}
