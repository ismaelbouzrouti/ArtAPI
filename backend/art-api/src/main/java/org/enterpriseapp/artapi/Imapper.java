package org.enterpriseapp.artapi;

public interface Imapper<E,D> {

    E convertToEntity(D dto);
    D convertToDTO(E entity);
}
