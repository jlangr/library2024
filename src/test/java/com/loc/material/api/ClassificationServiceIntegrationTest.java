package com.loc.material.api;

import domain.core.ClassificationApiFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassificationServiceIntegrationTest {
   static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
   static final String THE_ROAD_ISBN = "0-307-26543-9";
   static final String THE_ROAD_TITLE = "The Road";
   static final String THE_ROAD_YEAR = "2006";
   static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

   @Disabled("integration test; intermittent failure")
   @Test
   void liveRetrieve() {
      var liveService = ClassificationApiFactory.getService();

      var material = liveService.retrieveMaterial(THE_ROAD_ISBN);

      assertEquals(THE_ROAD_TITLE, material.getTitle());
      assertEquals(THE_ROAD_YEAR, material.getYear());
      assertEquals(THE_ROAD_AUTHOR, material.getAuthor());
      assertEquals(THE_ROAD_ISBN, material.getSourceId());
      assertEquals(THE_ROAD_CLASSIFICATION, material.getClassification());
   }
}
