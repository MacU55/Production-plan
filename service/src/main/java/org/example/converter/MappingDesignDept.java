package org.example.converter;

import org.example.domain.entity.DesignDept;
import org.example.dto.DesignDeptDTO;



public class MappingDesignDept implements Converter<DesignDept, DesignDeptDTO> {

    @Override
    public DesignDeptDTO convert(DesignDept entity) {
        DesignDeptDTO converterToDesignDeptDTO = new DesignDeptDTO();
        converterToDesignDeptDTO.setDesignId(entity.getId());
        converterToDesignDeptDTO.setOrderId(entity.getOrderId());
        converterToDesignDeptDTO.setDateDesignCompleted(entity.getDateDesignCompleted());
        converterToDesignDeptDTO.setDesignName(entity.getDesignName());

        return converterToDesignDeptDTO;
    }

    @Override
    public DesignDept convert(DesignDeptDTO entity) {
        DesignDept converterToDesignDept = new DesignDept();
        converterToDesignDept.setId(entity.getDesignId());
        converterToDesignDept.setOrderId(entity.getOrderId());
        converterToDesignDept.setDateDesignCompleted(entity.getDateDesignCompleted());
        converterToDesignDept.setDesignName(entity.getDesignName());

        return converterToDesignDept;
    }

}

