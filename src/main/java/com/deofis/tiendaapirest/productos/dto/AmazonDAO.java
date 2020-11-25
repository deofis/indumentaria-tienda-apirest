package com.deofis.tiendaapirest.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AmazonDAO {
    private String endpoint;
    private String bucketName;
}
