package com.fleetguard360.presentation.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-db")
public class TestDBController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public String checkDb() {
        try {
            entityManager.createNativeQuery("SELECT 1").getSingleResult();
            return "✅ Conexión OK";
        } catch (Exception e) {
            return "❌ Error en la conexión";
        }
    }
}
