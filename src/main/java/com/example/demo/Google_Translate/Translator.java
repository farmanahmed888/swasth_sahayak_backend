package com.example.demo.Google_Translate;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import com.google.api.services.translate.model.TranslationsResource;
import lombok.Builder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@RestController
@ResponseBody
@Builder
@RequestMapping("/translate")
public class Translator {

    @PostMapping("/text")
    private String translate(@RequestBody String text) throws IOException, GeneralSecurityException {
        Translate t = new Translate.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                null)
                .setApplicationName("Stackoverflow-Example")
                .build();

        Translate.Translations.List list = t.new Translations().list(
                Arrays.asList(text), // Pass in list of strings to be translated
                "es"); // Target language code, use lowercase

        // TODO: Set your API-Key from https://console.developers.google.com/
        list.setKey("your-api-key");

        TranslationsListResponse response = list.execute();
        StringBuilder translatedText = new StringBuilder();
        for (TranslationsResource translationsResource : response.getTranslations()) {
            translatedText.append(translationsResource.getTranslatedText());
        }
        return translatedText.toString();
    }
}
