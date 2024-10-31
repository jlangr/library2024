package com.loc.material.api;

import static org.mockito.ArgumentMatchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static util.StringUtils.removeHyphens;

@ExtendWith(MockitoExtension.class)
class ClassificationServiceTest {
   static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
   static final String THE_ROAD_ISBN = "0-307-26543-9";
   static final String THE_ROAD_TITLE = "The Road";
   static final String THE_ROAD_YEAR = "2006";
   static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

   @Mock
   IsbnClient isbnClient;

   ClassificationService service;

   @BeforeEach
   void setup() {
      service = new ClassificationService(isbnClient);
   }

   @Test
   void retrieveReturnsNullWhenNotFound() {
//        when(restTemplate.getForObject(contains(THE_ROAD_ISBN), eq(Map.class))).thenReturn(responseMap);
   }

   @Test
   void retrieveMaterialPopulatesFromResponse() {
      var responseMap = Map.of(
         "title", THE_ROAD_TITLE,
         "publish_date", THE_ROAD_YEAR,
         "lc_classifications", List.of(THE_ROAD_CLASSIFICATION),
         "authors", List.of(Map.of("name", THE_ROAD_AUTHOR)));
      when(isbnClient.retrieve(contains((removeHyphens(THE_ROAD_ISBN)))))
         .thenReturn(responseMap);
      // TODO when ...

      var material = service.retrieveMaterial(THE_ROAD_ISBN);

      assertMaterialDetailsForTheRoad(material);
   }

   // TODO   @Category(Slow.class)
   @Test
   void liveRetrieve() {
      var liveService = new ClassificationService(isbnClient);

      var material = liveService.retrieveMaterial(THE_ROAD_ISBN);

      assertMaterialDetailsForTheRoad(material);
   }

   void assertMaterialDetailsForTheRoad(Material material) {
      assertEquals(THE_ROAD_TITLE, material.getTitle());
      assertEquals(THE_ROAD_YEAR, material.getYear());
//      assertEquals(THE_ROAD_AUTHOR, material.getAuthor());
      assertEquals(THE_ROAD_ISBN, material.getSourceId());
      assertEquals(THE_ROAD_CLASSIFICATION, material.getClassification());
   }
}