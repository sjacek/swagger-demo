package com.github.sjacek.swagger_demo.config;

import com.google.common.base.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.invoke.MethodHandles;

import static com.github.sjacek.swagger_demo.config.SwaggerConfig.Version.v01;
import static java.lang.String.format;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String V01 = "v0.1";

    protected enum Version {
        v01(V01);

        private String version;

        Version(String version) {
            this.version = version;
        }

        @Contract(pure = true)
        public String toString() {
            return version;
        }
    }

    public static final String HELLO = "hello";

    @Bean
    public Docket all() {
        return new Docket(SWAGGER_2)
                .groupName("all")
                .apiInfo(helloApiInfo(v01));
    }

    private Docket helloDocket(Version version) {
        return new Docket(SWAGGER_2)
                .groupName("hello")
                .apiInfo(helloApiInfo(v01))
                .select()
                .paths(helloPaths(v01))
                .build();
    }

    @Bean
    public Docket helloApi() {
        return helloDocket(v01);
    }

    @NotNull
    private Predicate<String> helloPaths(Version version) {
        String s = format("/%s/%s.*", version, HELLO);
        return regex(s);
    }

    private ApiInfo helloApiInfo(@NotNull Version version) {
        ApiInfoBuilder ret = new ApiInfoBuilder()
                .title("e-Recepta API " + version)
                .contact(new Contact("Jacek Sztajnke", "", "jacek.sztajnke@gmail.com"))
                .version(version.toString());

        StringBuilder releaseNotes = new StringBuilder().append("Release Notes:\n\n");
        switch (version) {
            case v01:
                releaseNotes.append("Hello v0.1:\n\n")
                        .append("Initial version\n\n");
                break;
        }

        return ret.description(releaseNotes.toString()).build();
    }
}
