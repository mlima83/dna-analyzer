package br.com.ml.dnaanalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableSpringDataWebSupport
public class DnaAnalyserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DnaAnalyserApplication.class, args);
    }

}
