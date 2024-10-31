package com.loc.material.api;

public class ClassificationUtils {
   // TODO test
   public static String isbn10(String isbn) {
      return isbn.replaceAll("-", "");
   }
}
