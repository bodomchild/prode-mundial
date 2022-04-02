package com.facrod.prodemundial.service;

import com.facrod.prodemundial.dto.GroupDTO;
import com.facrod.prodemundial.exceptions.AppException;

import java.util.List;

public interface GroupService {

    List<GroupDTO> getAllGroups();

    GroupDTO getGroup(String group) throws AppException;

}
