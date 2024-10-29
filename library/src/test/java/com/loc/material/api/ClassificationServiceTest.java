package com.loc.material.api;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import testutil.Slow;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClassificationServiceTest {
    private static final String THE_ROAD_AUTHOR = "Cormac McCarthy";
    private static final String THE_ROAD_ISBN = "0-307-26543-9";
    private static final String THE_ROAD_TITLE = "The Road";
    private static final String THE_ROAD_YEAR = "2006";
    private static final String THE_ROAD_CLASSIFICATION = "PS3563.C337 R63 2006";

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private ClassificationService service;

    @Test
    public void retrieveMaterialPopulatesFromResponse() {
        var responseMap = Map.of(service.isbnKey(THE_ROAD_ISBN),
                Map.of("title", THE_ROAD_TITLE,
                        "publish_date", THE_ROAD_YEAR,
                        "classifications", Map.of("lc_classifications", List.of(THE_ROAD_CLASSIFICATION)),
                        "authors", List.of(Map.of("name", THE_ROAD_AUTHOR))));
        when(restTemplate.getForObject(contains(THE_ROAD_ISBN), eq(Map.class))).thenReturn(responseMap);

        var material = service.retrieveMaterial(THE_ROAD_ISBN);

        assertMaterialDetailsForTheRoad(material);
    }

    @Category(Slow.class)
    @Test
    public void liveRetrieve() {
        var liveService = new ClassificationService();

        var material = liveService.retrieveMaterial(THE_ROAD_ISBN);

        assertMaterialDetailsForTheRoad(material);
    }

    private void assertMaterialDetailsForTheRoad(Material material) {
        assertEquals(THE_ROAD_TITLE, material.getTitle());
        assertEquals(THE_ROAD_YEAR, material.getYear());
        assertEquals(THE_ROAD_AUTHOR, material.getAuthor());
        assertEquals(THE_ROAD_ISBN, material.getSourceId());
        assertEquals(THE_ROAD_CLASSIFICATION, material.getClassification());
    }
}