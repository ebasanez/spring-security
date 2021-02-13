package com.example.demo.configuration.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Create a different class, as this must extend GlobalMethodSecurityConfiguration.
 * This is useful to handle all method security in a centralized way (without any @Preauthorize annotation in the code)
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AclSecurityConfig extends GlobalMethodSecurityConfiguration {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
		// 9.3 Configure method security using AOP
		// Add custom access rules to methods here instead of doing that in annotations
		Map<String, List<ConfigAttribute>> methodMap = new HashMap<>();
		methodMap.put("com.example.demo.controller.DemoController.byeWorld", SecurityConfig.createList("ROLE_USER"));
		return new MapBasedMethodSecurityMetadataSource(methodMap);
	}

	// 11.3 ACL Configuration

	// Use parent class extension point to register our ACL security expression handler
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(aclPermissionEvaluator());
		return expressionHandler;
	}

	@Bean
	public AclPermissionEvaluator aclPermissionEvaluator() {
		return new AclPermissionEvaluator(aclService());
	}

	@Bean
	public JdbcMutableAclService aclService() {
		final JdbcMutableAclService service = new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
		service.setClassIdentityQuery("SELECT @@IDENTITY");
		service.setSidIdentityQuery("SELECT @@IDENTITY");
		return service;
	}

	@Bean
	LookupStrategy lookupStrategy() {
		return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), permissionGrantingStrategy());
	}

	@Bean
	public AclCache aclCache() {
		return new SpringCacheBasedAclCache(
				cacheManager.getCache("aclCache"),
				permissionGrantingStrategy(),
				aclAuthorizationStrategy());
	}

	@Bean
	AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ADMIN"));
	}

	PermissionGrantingStrategy permissionGrantingStrategy() {
		return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
	}

}
