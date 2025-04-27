package com.practice.service;

import com.practice.dto.GroupMemberDetailDTO;
import com.practice.dto.GroupMemberDetailFullDTO;
import com.practice.entity.GroupMemberDetail;
import com.practice.req.GroupMemberDetailReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GroupMemberDetailService {
    GroupMemberDetailDTO addOrUpdateDetail(GroupMemberDetailReq req);


    void updateGroupScore(Long groupId, Float groupScore);

    List<GroupMemberDetailDTO> getDetailsByGroupId(Long groupId);
    String uploadReportFile(Long groupMemberId, MultipartFile file) throws IOException;

    GroupMemberDetailFullDTO getGroupMemberDetailsFull(Long groupId);

}
