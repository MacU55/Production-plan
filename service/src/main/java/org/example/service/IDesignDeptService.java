package org.example.service;

import org.example.dto.DesignDeptDTO;
import org.example.exception.ServiceException;

import java.util.List;

    public interface IDesignDeptService {

    DesignDeptDTO save(DesignDeptDTO designOrder) throws ServiceException;

    DesignDeptDTO update(DesignDeptDTO designOrder) throws ServiceException;

    DesignDeptDTO findByDesignId(int designId) throws ServiceException;

    List<DesignDeptDTO> findDesignDepts() throws ServiceException;
}

