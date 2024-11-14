package com.loc.material.api;

public record Material(String sourceId,
                       String author,
                       String title,
                       String classification,
                       MaterialType materialType,
                       String year) {
   public Material(String sourceId, String author, String title, String classification,
                   String year) {
      this(sourceId, author, title, classification, MaterialType.BOOK, year);
   }
}
