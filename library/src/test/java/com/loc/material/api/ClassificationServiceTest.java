package com.loc.material.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import testutil.Slow;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static util.StringUtils.removeHyphens;

@RunWith(MockitoJUnitRunner.class)
public class ClassificationServiceTest {
   private static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
   private static final String THE_ROAD_ISBN = "0-307-26543-9";
   private static final String THE_ROAD_TITLE = "The Road";
   private static final String THE_ROAD_YEAR = "2006";
   private static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

   @Mock
   private IsbnClient isbnClient;

   private ClassificationService service;

   @Before
   public void setup() {
      service = new ClassificationService(isbnClient);
   }

   @Test
   public void retrieveReturnsNullWhenNotFound() {
//        when(restTemplate.getForObject(contains(THE_ROAD_ISBN), eq(Map.class))).thenReturn(responseMap);
   }

   @Test
   public void retrieveMaterialPopulatesFromResponse() {
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

   @Category(Slow.class)
   @Test
   public void liveRetrieve() {
      var liveService = new ClassificationService(isbnClient);

      var material = liveService.retrieveMaterial(THE_ROAD_ISBN);

      System.out.println("author: " + material.getAuthor());
      assertMaterialDetailsForTheRoad(material);
   }

   private void assertMaterialDetailsForTheRoad(Material material) {
      assertEquals(THE_ROAD_TITLE, material.getTitle());
      assertEquals(THE_ROAD_YEAR, material.getYear());
//      assertEquals(THE_ROAD_AUTHOR, material.getAuthor());
      assertEquals(THE_ROAD_ISBN, material.getSourceId());
      assertEquals(THE_ROAD_CLASSIFICATION, material.getClassification());
   }
}