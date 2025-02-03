package com.projeto.avapay.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		// aqui, vamos descrever como o swagger  -aplicativo com front para testar os endpoints -
		// deve se comportar apara auxiliar nos testes da aplicação
		return new OpenAPI() 
			.components(
					// é importante esta instrução para lidar com as rotas protegidas
						new Components().addSecuritySchemes("basicAuth", new SecurityScheme()
									.type(SecurityScheme.Type.HTTP)
									.scheme("basic")
								))
							
							.addSecurityItem(
											new SecurityRequirement()
												.addList("basicAuth"))
												.info(new Info()
												.title("Spring boot API")
												.version("1.0")
												.description("API de testes do Springboot App"));			
					
		}
	}



