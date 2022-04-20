package com.example.books.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum Class {
    CLASS_1(1), CLASS_2(2), CLASS_3(3), CLASS_4(4), CLASS_5(5), CLASS_6(6),
    CLASS_7(7), CLASS_8(8), CLASS_9(9), CLASS_10(10), CLASS_11(11);

    private final int value;

    Class(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static final Map<Integer, Class> map;

    static {
        map = new HashMap<Integer, Class>();
        for (Class v : Class.values()) {
            map.put(v.value, v);
        }
    }

    public static Class findByKey(int i) {
        return map.get(i);
    }
}
