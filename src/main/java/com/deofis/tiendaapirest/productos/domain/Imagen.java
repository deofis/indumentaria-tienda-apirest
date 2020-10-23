package com.deofis.tiendaapirest.productos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Imagen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String path;
}
