package cloud4.team4.travelog.domain.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BoardMvcConfig implements WebMvcConfigurer {
    //private String resourcePath ="/add-board/**";
    //"file:///Users/Hyun/Desktop/exppic/"

    @Override
    public void  addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/seoul/add-board/**") // --1
                .addResourceLocations("file:///Users/Hyun/Desktop/exppic/"); //--2
    }
}