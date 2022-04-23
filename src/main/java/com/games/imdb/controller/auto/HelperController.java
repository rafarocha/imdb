package com.games.imdb.controller.auto;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HelperController {

    public static Map<String, String> getAllHeaders(HttpServletRequest request) {
        Map<String, String> mapHeaders = new HashMap<String, String>();
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String param = headers.nextElement();
            mapHeaders.put(param, request.getHeader(param));
        }
        return mapHeaders;
    }
    
}