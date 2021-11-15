package com.example.agrawal_classes.utils;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
@ConfigurationPropertiesBinding
public final class DateConvertor implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return Date.valueOf(source);
    }

}
