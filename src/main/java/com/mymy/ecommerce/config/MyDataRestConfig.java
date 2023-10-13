package com.mymy.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.mymy.ecommerce.entity.Country;
import com.mymy.ecommerce.entity.Order;
import com.mymy.ecommerce.entity.Product;
import com.mymy.ecommerce.entity.ProductCategory;
import com.mymy.ecommerce.entity.State;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	@Value("${spring.data.rest.base-path}")
	private String basePath;
	
	@Value("${allowed.origins}")
	private String[] theAllowedOrigins;	
 	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig (EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		
		HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};
		
		//disable HTTP methods for Class: PUT, POST, and DELETE
		disableHttpMethods(Product.class, config, theUnsupportedActions);
		disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);
		disableHttpMethods(Country.class, config, theUnsupportedActions);
		disableHttpMethods(State.class, config, theUnsupportedActions);
		disableHttpMethods(Order.class, config, theUnsupportedActions);		
		
		//call an internal helper method
		exposeIds(config);
		
		// configure cors mapping
		cors.addMapping(basePath + "/**").allowedOrigins(theAllowedOrigins);
	}

	private ExposureConfigurer disableHttpMethods(Class theClass, RepositoryRestConfiguration config,
			HttpMethod[] theUnsupportedActions) {
		return config.getExposureConfiguration()
				.forDomainType(theClass)
				.withItemExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {
		
		//expose entity ids
		
		//-get a list of all entity classes from the entity manager
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		
		//-create an array of the entity types
		List<Class> entityClasses = new ArrayList<>();
		
		//-get the entity type for the entities
		for (EntityType tempEntityType: entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}
		
		//-expose the entity ids for the array of entity/domain types
		Class[] domainTypeClasses = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypeClasses);

		
	}
}
