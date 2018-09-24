package com.nelioalves.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

    public static String decodeParam(String texto) {
        try {
            return URLDecoder.decode(texto, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Integer> decodeIntList(String texto) {
        List<Integer> numeros = new ArrayList<>();
        for(String text : texto.split(",")) {
            numeros.add(Integer.valueOf(text.trim()));
        }
        return numeros;
        // ou:
        //return Arrays.asList(texto.split(",")).stream().map(text -> Integer.valueOf(text.trim())).collect(Collectors.toList());
    }
}
