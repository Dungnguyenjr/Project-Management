package com.practice.service;

import com.practice.dto.GroupMemberDetailDTO;
import com.practice.entity.GroupMemberDetail;
import com.practice.req.GroupMemberDetailReq;

import java.util.List;

public interface GroupMemberDetailService {
    GroupMemberDetailDTO addOrUpdateDetail(GroupMemberDetailReq req);


    void updateGroupScore(Long groupId, Float groupScore);

    List<GroupMemberDetailDTO> getDetailsByGroupId(Long groupId);

}
