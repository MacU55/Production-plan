package org.example.converter;

import org.example.domain.entity.BaseEntity;
import org.example.dto.BaseDTO;

public interface Converter<K extends BaseEntity, T extends BaseDTO> {
    
    K convert(T dto);

    T convert(K entity);
}
