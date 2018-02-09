package pl.piomin.microservices.edge;

import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableZuulProxy
@RestController
@EnableSwagger2

public class Application {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

	@Bean
	public AlwaysSampler defaultSampler() {
	  return new AlwaysSampler();
	}
	
	 @Bean
	    public Docket api() throws IOException, XmlPullParserException {
	        MavenXpp3Reader reader = new MavenXpp3Reader();
	        Model model = reader.read(new FileReader("pom.xml"));
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select() 
//	          .apis(RequestHandlerSelectors.basePackage("org.springframework.samples.petclinic.customers.web"))
	          .paths(PathSelectors.any())                          
	          .build().apiInfo(new ApiInfo("Gateway Api Documentation", "Documentation automatically generated", model.getParent().getVersion(), null, new Contact("Kennedy Torkura", "kennedy.wordpress.com", "run2obtain@gmail.com"), null, null));                                           
	    }
	
}
