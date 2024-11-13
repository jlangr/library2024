package com.loc.material.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassificationServiceTest {
   static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
   static final String THE_ROAD_ISBN = "0-307-26543-9";
   static final String THE_ROAD_TITLE = "The Road";
   static final String THE_ROAD_YEAR = "2006";
   static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

   @Mock
   IsbnClient isbnClient;
   @Mock
   AuthorClient authorClient;

   ClassificationService service;

   @BeforeEach
   void setup() {
      service = new ClassificationService(isbnClient, authorClient);
   }

   @Test
   void retrieveReturnsNullWhenNotFound() {
      when(isbnClient.retrieve(anyString())).thenReturn(null);

      var material = service.retrieveMaterial(THE_ROAD_ISBN);

      assertNull(material);
   }

   @Test
   void retrieveMaterialPopulatesFromResponse() {
      when(isbnClient.retrieve(contains(THE_ROAD_ISBN)))
         .thenReturn(Map.of(
            "title", THE_ROAD_TITLE,
            "publish_date", THE_ROAD_YEAR,
            "lc_classifications", List.of(THE_ROAD_CLASSIFICATION),
            "authors", List.of(Map.of("key", "/author/O123"))));
      when(authorClient.retrieve("/author/O123"))
         .thenReturn(Map.of("name", "Cormac McCarthy"));

      var material = service.retrieveMaterial(THE_ROAD_ISBN);

      assertMaterialDetailsForTheRoad(material);
   }


   void assertMaterialDetailsForTheRoad(Material material) {
      assertEquals(THE_ROAD_TITLE, material.title());
      assertEquals(THE_ROAD_YEAR, material.year());
      assertEquals(THE_ROAD_AUTHOR, material.author());
      assertEquals(THE_ROAD_ISBN, material.sourceId());
      assertEquals(THE_ROAD_CLASSIFICATION, material.classification());
   }
}