package lk.ijse.dep13.backendexpensemanager;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.dep13.backendexpensemanager.interceptor.SecurityInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collection;


@Configuration
@ComponentScan
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/transactions/**").addPathPatterns("/user/me").addPathPatterns("/user/me/upload");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://trackflow-30b47.web.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    @Bean
    public FilterRegistrationBean<MultipartFilter> multipartFilter() {
        FilterRegistrationBean<MultipartFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MultipartFilter());
        registrationBean.addUrlPatterns("/user/me/upload/*");  // Adjust as needed
        return registrationBean;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ServletContextInitializer initializer() {
        return servletContext -> {
            servletContext.getSessionCookieConfig().setSecure(true); // important for HTTPS
            servletContext.getSessionCookieConfig().setHttpOnly(true);
            servletContext.getSessionCookieConfig().setName("JSESSIONID");
            servletContext.getSessionCookieConfig().setPath("/");
        };
    }

    @Bean
    public FilterRegistrationBean<Filter> sameSiteFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter((request, response, chain) -> {
            chain.doFilter(request, response);
            if (response instanceof HttpServletResponse) {
                HttpServletResponse res = (HttpServletResponse) response;
                Collection<String> headers = res.getHeaders("Set-Cookie");
                boolean firstHeader = true;
                for (String header : headers) {
                    if (header.contains("JSESSIONID")) {
                        String updatedHeader = header + "; SameSite=None; Secure";
                        if (firstHeader) {
                            res.setHeader("Set-Cookie", updatedHeader);
                            firstHeader = false;
                        } else {
                            res.addHeader("Set-Cookie", updatedHeader);
                        }
                    }
                }
            }
        });
        return registrationBean;
    }



}
