package com.techlabs.ecommerce.config;

import com.techlabs.ecommerce.entity.Country;
import com.techlabs.ecommerce.entity.Product;
import com.techlabs.ecommerce.entity.ProductCategory;
import com.techlabs.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MyRestDataConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyRestDataConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for Product: PUT, POST, DELETE and PATCH
        Class clazz = Product.class;
        disableUnsupportedActions(config, clazz, theUnsupportedActions);

        // disable HTTP methods for ProductCategory: PUT, POST, DELETE and PATCH
        clazz = ProductCategory.class;
        disableUnsupportedActions(config, clazz, theUnsupportedActions);

        // disable HTTP methods for Country: PUT, POST, DELETE and PATCH
        clazz = Country.class;
        disableUnsupportedActions(config, clazz, theUnsupportedActions);

        // disable HTTP methods for State: PUT, POST, DELETE and PATCH
        clazz = State.class;
        disableUnsupportedActions(config, clazz, theUnsupportedActions);

        // call for expose ids
        exposeIds(config);
    }

    private static void disableUnsupportedActions(RepositoryRestConfiguration config, Class clazz, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(clazz)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        List<Class> entityClasses = entities.stream().map(EntityType::getJavaType).collect(Collectors.toList());
        config.exposeIdsFor(entityClasses.toArray(new Class[0]));
    }
}
