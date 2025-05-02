package com.practice.service;

import com.practice.dto.GroupDTO;
import com.practice.dto.GroupDetailDTO;
import com.practice.dto.GroupListDTO;
import com.practice.req.GroupCreateReq;
import com.practice.req.GroupUpdateReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {

    GroupDTO createGroup(GroupCreateReq groupCreateReq);
    GroupDTO updateGroup(long id, GroupUpdateReq groupUpdateReq);
    void deleteGroup(long id);

    Page<GroupListDTO> getAllGroups(Pageable pageable);
    GroupDetailDTO getGroupDetail(Long groupId, String accountId);

}
