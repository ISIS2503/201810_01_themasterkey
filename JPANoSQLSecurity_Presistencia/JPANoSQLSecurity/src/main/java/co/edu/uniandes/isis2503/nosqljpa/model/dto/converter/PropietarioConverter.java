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


import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.PropietarioDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.PropietarioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author josedanielcardenasrincon
 */
public class PropietarioConverter {
    
    public static PropietarioConverter CONVERTER=new PropietarioConverter();

    public PropietarioConverter() {
    }
    
    public PropietarioDTO entityToDto(PropietarioEntity entity) {
        PropietarioDTO dto=new PropietarioDTO();
        dto.setId(entity.getId());
        dto.setEdad(entity.getEdad());
        dto.setInmuebles(entity.getInmuebles());
        dto.setNombre(entity.getNombre());
        return dto;
    }
    
    public PropietarioEntity dtoToEntity(PropietarioDTO dto) {
        PropietarioEntity entity=new PropietarioEntity();
        entity.setId(dto.getId());
        entity.setEdad(dto.getEdad());
        entity.setInmuebles(dto.getInmuebles());
        dto.setNombre(dto.getNombre());
        return entity;
    }
    
    public List<PropietarioDTO> listEntitiesToListDTOs(List<PropietarioEntity> entities) {
        ArrayList<PropietarioDTO> dtos = new ArrayList<>();
        for (PropietarioEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    public List<PropietarioEntity> listDTOsToListEntities(List<PropietarioDTO> dtos) {
        ArrayList<PropietarioEntity> entities = new ArrayList<>();
        for (PropietarioDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
