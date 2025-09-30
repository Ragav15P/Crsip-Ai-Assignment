package in.Ragav.Intern.CrispAi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CorsConfig implements WebMvcConfigurer 
{
      public void addCorsMappings(CorsRegistry registry)
      {
    	  registry.addMapping("/api/**")
    	          .allowedOrigins("http://localhost:3006")
    	          .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
    	          .allowedHeaders("*");
      }
}
