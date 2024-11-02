package com.loc.material.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// This class uses the Open Library API; see https://openlibrary.org/dev/docs/api/books

public class ClassificationService implements ClassificationApi {
   private final IsbnClient isbnClient;
   private final AuthorClient authorClient;

   public ClassificationService(IsbnClient isbnClient, AuthorClient authorClient) {
      this.authorClient = authorClient;
      this.isbnClient = isbnClient;
   }

   @Override
   public Material retrieveMaterial(String sourceId) {
      var materialJsonObject = isbnClient.retrieve(sourceId);
      if (materialJsonObject == null) return null;

      var author = retrieveFirstAuthor(materialJsonObject);

      return createMaterial(sourceId, materialJsonObject, author);
   }

   private Map<String, Object> retrieveFirstAuthor(Map<String, Object> jsonObject) {
      var authors = (List<Map<String,Object>>) jsonObject.get("authors");
      var firstAuthorKey = getString(authors.getFirst(), "key");
      return authorClient.retrieve(firstAuthorKey);
   }

   private Material createMaterial(String sourceId, Map<String, Object> materialJsonObject, Map<String, Object> authorJsonObject) {
      var material = new Material();
      material.setSourceId(sourceId);
      material.setFormat(MaterialType.BOOK);
      material.setTitle(getString(materialJsonObject, "title"));
      material.setYear(getString(materialJsonObject, "publish_date"));
      material.setAuthor(getString(authorJsonObject, "name"));
      material.setClassification(getLibraryOfCongressClassification(materialJsonObject));
      return material;
   }

   private String getLibraryOfCongressClassification(Map<String, Object> response) {
      var classifications = getList(response, "lc_classifications");
      return classifications.get(0);
   }

   @SuppressWarnings("unchecked")
   private List<String> getList(Map<String, Object> response, String key) {
      return new ArrayList<>((List<String>) response.get(key));
   }

   private String getString(Map<String, Object> map, String key) {
      return (String) map.get(key);
   }
}