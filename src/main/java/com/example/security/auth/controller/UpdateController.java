package com.example.security.auth.controller;

import com.example.security.auth.request.UpdateRequest;
import com.example.security.auth.service.UpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateController {

    @Autowired
    private final UpdateService updateService;

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody UpdateRequest request) {
        updateService.update(request);
        return ResponseEntity.ok(updateService.update(request));
    }
}
