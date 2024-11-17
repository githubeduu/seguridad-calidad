package com.example.seguridad_calidad.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class UploadController {
    @GetMapping("/upload")
    public String showUpload() {
        return "upload";
    }
}