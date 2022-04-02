package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.GroupDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{group}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable("group") String group) throws AppException {
        return ResponseEntity.ok(groupService.getGroup(group));
    }

}
