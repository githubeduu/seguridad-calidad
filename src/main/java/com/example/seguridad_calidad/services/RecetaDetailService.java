package com.example.seguridad_calidad.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.seguridad_calidad.Model.RecetaDetail;


@Service
public class RecetaDetailService {
      private final RestTemplate restTemplate;

      public RecetaDetailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

public RecetaDetail getRecetasDetail(int id) {
        String url = "http://localhost:8087/recetas/" + id + "/detalle";;
        return restTemplate.getForObject(url, RecetaDetail.class);
  }
            

}
