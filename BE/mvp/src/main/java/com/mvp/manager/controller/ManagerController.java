package com.mvp.manager.controller;

import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;
import com.mvp.manager.service.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping
    public List<ManagerDTO> getAllManagers(){
        return managerService.getAllManagers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDTO> findManagerById(@PathVariable int id){
        ManagerDTO managerDTO = managerService.findManagerById(id);
        if(managerDTO != null){
            return ResponseEntity.ok(managerDTO);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ManagerDTO createManager(@RequestBody ManagerDTO managerDTO){
        return managerService.createManager(managerDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerDTO> updateManager(@PathVariable int id, @RequestBody ManagerDTO managerDTO){
        ManagerDTO managerDTOUpdated = managerService.updateManager(id, managerDTO);
        if(managerDTOUpdated != null){
            return ResponseEntity.ok(managerDTOUpdated);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManagerDTO> deleteManager(@PathVariable int id){
        managerService.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
