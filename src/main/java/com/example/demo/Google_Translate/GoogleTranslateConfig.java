package com.example.demo.Google_Translate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleTranslateConfig {

    @Bean
    public Translate translate() {
        return TranslateOptions.newBuilder().setApiKey("YOUR_API_KEY").build().getService();
    }
}
