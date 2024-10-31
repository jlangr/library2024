package com.loc.material.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class AuthorClient {
   public static final String SERVER = "https://openlibrary.org";
   private final Http http;

   public AuthorClient(Http http) {
      this.http = http;
   }

   public Map<String, Object> retrieve(String authorKey) {
      var url = url(authorKey + ".json");
      return http.retrieveJson(url);
   }

   private String encode(String text) {
      try {
         return URLEncoder.encode(text, "UTF-8");
      } catch (UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }

   private String url(String doc) {
      return SERVER + doc;
   }
}
