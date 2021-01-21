package com.deofis.tiendaapirest.ecommerce.domain;

import com.deofis.tiendaapirest.ecommerce.exceptions.SingletonException;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "banner_config")
@Data
public class BannerConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Banner> banners;

    private static BannerConfig configuradorBanner;

    public static BannerConfig getBanner(Long id, List<Banner> banners) {
        if (configuradorBanner == null)
            configuradorBanner = new BannerConfig(id, banners);
        return configuradorBanner;
    }

    public BannerConfig() {
        if (configuradorBanner != null)
            throw new SingletonException("Ya existe una instancia de configuración del banner");
    }

    private BannerConfig(Long id, List<Banner> banners) {
        this.id = id;
        this.banners = banners;
    }
}
