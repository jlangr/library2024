package com.loc.material.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// This class uses the Open Library API; see https://openlibrary.org/dev/docs/api/books

public class ClassificationService implements ClassificationApi {
   private final IsbnClient isbnClient;
   private Http http;

   public ClassificationService() {
      this.http = new Http();
      isbnClient = new IsbnClient(http);
   }

   public ClassificationService(Http http) {
      this.http = http;
      isbnClient = new IsbnClient(http);
   }

   @Override
   public Material retrieveMaterial(String sourceId) {
      var materialJsonObject = isbnClient.retrieve(sourceId);
      return createMaterial(sourceId, materialJsonObject);
   }

   private Material createMaterial(String sourceId, Map<String, Object> jsonObject) {
      var material = new Material();
      material.setSourceId(sourceId);
      material.setFormat(MaterialType.BOOK);
      material.setTitle(getString(jsonObject, "title"));
      material.setYear(getString(jsonObject, "publish_date"));



      var authors = (List<Map<String,Object>>) jsonObject.get("authors");
      System.out.println("authors: " + authors);
//      var firstAuthorName = getString(authors.getFirst(), "name");
      var firstAuthorKey = getString(authors.getFirst(), "key");
      var authorClient = new AuthorClient(http);
      var author = authorClient.retrieve(firstAuthorKey);
      System.out.println("author: " + author);

      material.setAuthor(getString(author, "name"));

      material.setClassification(getLibraryOfCongressClassification(jsonObject));
      return material;
   }

   private String getLibraryOfCongressClassification(Map<String, Object> response) {
      @SuppressWarnings("unchecked")
      var classifications = getList(response, "lc_classifications");
      return classifications.get(0);
   }

   @SuppressWarnings("unchecked")
   private List<String> getList(Map<String, Object> response, String key) {
      return new ArrayList<>((List<String>) response.get(key));
   }

//   private String getFirstAuthorName(Map<String, Object> map) {
//      var firstAuthor = getMap(getList(map, "authors"), 0);
//      return (String) firstAuthor.get("name");
//   }

   @SuppressWarnings("unchecked")
   private Map<String, Object> getMap(List<Object> list, int index) {
      return (Map<String, Object>) list.get(index);
   }

   private String getString(Map<String, Object> map, String key) {
      return (String) map.get(key);
   }
}