package com.example.books.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    UZBEK("uzbek"), RUSSIAN("russian"), ENGLISH("english");

    private static final Map<String, Language> map;

    static {
        map = new HashMap<>();
        for (Language v : Language.values()) {
            map.put(v.lang, v);
        }
    }

    private final String lang;

    Language(String lang) {
        this.lang = lang;
    }

    public static Language findByKey(String i) {
        return map.get(i);
    }

    public String getLang() {
        return lang;
    }
}
