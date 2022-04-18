package com.facrod.prodemundial.controller;

import com.facrod.prodemundial.dto.GroupDTO;
import com.facrod.prodemundial.dto.TeamDTO;
import com.facrod.prodemundial.exceptions.AppException;
import com.facrod.prodemundial.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GroupControllerTest {

    @Mock
    private GroupService service;

    private GroupController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);
        controller = new GroupController(service);
    }

    @Test
    void getAllGroups() {
        var groups = List.of(getGroup());
        var expected = ResponseEntity.ok(groups);

        when(service.getAllGroups()).thenReturn(groups);

        var actual = controller.getAllGroups();

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void getGroup_ok() throws AppException {
        var group = getGroup();
        var expected = ResponseEntity.ok(group);

        when(service.getGroup("C")).thenReturn(group);

        var actual = controller.getGroup("C");

        assertNotNull(actual);
        assertNotNull(actual.getBody());
        assertEquals(expected, actual);
    }

    @Test
    void getGroup_error() throws AppException {
        var expected = new AppException(HttpStatus.NOT_FOUND, "El grupo 'C' no existe");

        when(service.getGroup("C")).thenThrow(expected);

        var actual = assertThrows(AppException.class, () -> controller.getGroup("C"));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private GroupDTO getGroup() {
        var arg = new TeamDTO();
        arg.setId("ARG");
        arg.setName("Argentina");

        var mex = new TeamDTO();
        mex.setId("MEX");
        mex.setName("Mexico");

        var ksa = new TeamDTO();
        ksa.setId("KSA");
        ksa.setName("Arabia Saudita");

        var pol = new TeamDTO();
        pol.setId("POL");
        pol.setName("Polonia");

        var group = new GroupDTO();
        group.setGroup("C");
        group.setTeams(List.of(arg, mex, ksa, pol));

        return group;
    }

}