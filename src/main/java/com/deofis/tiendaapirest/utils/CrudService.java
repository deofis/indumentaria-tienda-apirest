package com.deofis.tiendaapirest.utils;

public interface CrudService<T, ID> {

    T save(T object);

}
