package com.convave.mall2api.config;


import com.convave.mall2api.controller.fomatter.LocalDateFormatter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class CustomServletConfig implements WebMvcConfigurer {


	@Override
	public void addFormatters(FormatterRegistry registry) {

		log.info("-------------");
		log.info("addFormatter");

		registry.addFormatter(new LocalDateFormatter());

//		WebMvcConfigurer.super.addFormatters(registry);
	}


	// react <> springboot connect
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.maxAge(500)
				.allowedOrigins("*")
				.allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS");
		// OPTIONS == pre flight (미리 연결이 가능한지 찔러보는 기능)


		WebMvcConfigurer.super.addCorsMappings(registry);
	}
}
