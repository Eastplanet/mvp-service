package com.mvp.manager.controller;

import com.mvp.manager.dto.ManagerDTO;
import com.mvp.manager.entity.Manager;
import com.mvp.manager.service.ManagerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    /**
     * 로그인
     *
     * @param managerDTO
     * @return
     */
    @PostMapping("/login")
    public ManagerDTO loginManager(@RequestBody ManagerDTO managerDTO) {
        return managerService.login(managerDTO);
    }

    /**
     * 관리자 등록
     *
     * @param managerDTO
     * @return
     */
    @PostMapping
    public ManagerDTO createManager(@RequestBody ManagerDTO managerDTO) {
        return managerService.createManager(managerDTO);
    }

    /**
     * 관리자 수정
     * @param managerDTO
     * @return
     */
    @PutMapping
    public ResponseEntity<ManagerDTO> updateManager(@RequestBody ManagerDTO managerDTO){
        ManagerDTO updatedManagerDTO = managerService.updateManager(managerDTO);

        if(updatedManagerDTO != null){
            return ResponseEntity.ok(updatedManagerDTO);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 관리자 삭제 (이메일)
     * @param email
     * @return
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<ManagerDTO> deleteManager(@PathVariable String email) {
        if (managerService.deleteManager(email)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
