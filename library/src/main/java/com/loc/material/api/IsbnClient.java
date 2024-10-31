package com.loc.material.api;

import java.util.Map;

public class IsbnClient {
   public static final String SERVER = "https://openlibrary.org";
   private final Http http;

   public IsbnClient(Http http) {
      this.http = http;
   }

   public Map<String, Object> retrieve(String sourceId) {
      var url = url(findByDoc(isbnKey(sourceId))); // isbnKey?
      System.out.println("URL: " + url);
      return http.retrieveJson(url);
   }

   private String url(String doc) {
      return SERVER + doc;
   }

   private String findByDoc(String isbn) {
      return "/isbn/" + ClassificationUtils.isbn10(isbn) + ".json";
   }

   String isbnKey(String sourceId) {
      return "ISBN:" + sourceId;
   }
}
