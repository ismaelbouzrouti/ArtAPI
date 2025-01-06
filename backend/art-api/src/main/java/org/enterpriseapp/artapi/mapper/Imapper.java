package org.enterpriseapp.artapi.mapper;

public interface Imapper<E,D> {

    E convertToEntity(D dto);
    D convertToDTO(E entity);
}
