package com.back.backregister.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;



import java.util.ArrayList;

/**
 * @ author: rose
 * @ date: 2024-11-19
 * @ version: 1.0
 * @ description:
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "后端注册的api接口",
                version = "1.0.0",
                description = "这是注册的接口",
                contact = @Contact(
                        name = "rose",
                        url = "https://micgo.top",
                        email = "1320104286@qq.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class SwaggerConfig {
}

