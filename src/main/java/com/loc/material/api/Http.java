package com.loc.material.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import util.RestUtil;

import java.util.Map;

public class Http {
   private final RestTemplate template;

   public Http() {
      template = RestUtil.createRestTemplate();
   }

   public Http(RestTemplate restTemplate) {
      template = restTemplate;
   }

   @SuppressWarnings("unchecked")
   public Map<String, Object> retrieveJson(String url) {
      try {
         // TODO
//         System.out.println("retrieving url: " + url);
         return template.getForObject(url, Map.class);

      } catch (HttpClientErrorException e) {
         if (e.getStatusCode() == HttpStatus.NOT_FOUND)
            return null;
         throw new RuntimeException(e.getCause());
      }
      catch (Exception e) {
         throw new RuntimeException(e.getCause());
      }
   }
}
