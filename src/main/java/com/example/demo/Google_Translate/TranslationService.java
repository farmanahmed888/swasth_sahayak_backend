package com.example.demo.Google_Translate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final Translate translate;

    @Autowired
    public TranslationService(Translate translate) {
        this.translate = translate;
    }

    public String translateText(String text, String targetLanguage) {
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage(targetLanguage));
        return translation.getTranslatedText();
    }
}
