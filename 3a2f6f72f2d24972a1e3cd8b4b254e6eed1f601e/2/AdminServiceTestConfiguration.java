package com.ctrip.apollo;

import com.ctrip.apollo.common.controller.HttpMessageConverterConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.ctrip.apollo.adminservice.AdminServiceApplication;

@Configuration
@ComponentScan(excludeFilters = {@Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
        SampleAdminServiceApplication.class, AdminServiceApplication.class,
        HttpMessageConverterConfiguration.class})})
@EnableAutoConfiguration
public class AdminServiceTestConfiguration {

}
