package com.nelioalves.cursomc.api.v1.controller.utils;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class URL {

    public static String decodeParam(String texto) {
        return URLDecoder.decode(texto, UTF_8);
    }

    public static List<Integer> decodeIntList(String texto) {
        List<Integer> numeros = new ArrayList<>();
        for (String text : texto.split(",")) {
            numeros.add(Integer.valueOf(text.trim()));
        }
        return numeros;
        // ou:
        //return Arrays.asList(texto.split(",")).stream().map(text -> Integer.valueOf(text.trim())).collect(Collectors.toList());
    }
}
