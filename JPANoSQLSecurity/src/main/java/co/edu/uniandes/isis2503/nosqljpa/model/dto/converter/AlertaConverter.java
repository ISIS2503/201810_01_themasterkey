/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlertaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.AlertaEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josedanielcardenasrincon
 */
public class AlertaConverter {
    
    public static AlertaConverter CONVERTER=new AlertaConverter();

    public AlertaConverter() {
    }
    
    public AlertaDTO entityToDto(AlertaEntity entity) {
        AlertaDTO dto=new AlertaDTO();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setFecha(entity.getFecha());
        dto.setIdAdministrador(entity.getIdAdministrador());
        dto.setIdCerradura(entity.getIdCerradura());
        dto.setIdConjunto(entity.getIdConjunto());
        dto.setIdHub(entity.getIdHub());
        dto.setIdPropietario(entity.getIdPropietario());
        dto.setIdInmueble(entity.getIdInmueble());
        return dto;
    }
    
    public AlertaEntity dtoToEntity(AlertaDTO dto) {
        AlertaEntity entity=new AlertaEntity();
        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        entity.setFecha(dto.getFecha());
        entity.setIdAdministrador(dto.getIdAdministrador());
        entity.setIdCerradura(dto.getIdCerradura());
        entity.setIdConjunto(dto.getIdConjunto());
        entity.setIdHub(dto.getIdHub());
        entity.setIdPropietario(dto.getIdPropietario());
        entity.setIdInmueble(dto.getIdInmueble());
        return entity;
    }
    
    public List<AlertaDTO> listEntitiesToListDTOs(List<AlertaEntity> entities) {
        ArrayList<AlertaDTO> dtos = new ArrayList<>();
        for (AlertaEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    public List<AlertaEntity> listDTOsToListEntities(List<AlertaDTO> dtos) {
        ArrayList<AlertaEntity> entities = new ArrayList<>();
        for (AlertaDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
