package com.deofis.tiendaapirest.productos.services;

public interface CrudService<T, ID> {

    T save(T object);

}
