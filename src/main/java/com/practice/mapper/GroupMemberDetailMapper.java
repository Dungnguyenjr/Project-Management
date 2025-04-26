package com.practice.mapper;

import com.practice.dto.GroupMemberDetailDTO;
import com.practice.entity.GroupMemberDetail;
import com.practice.req.GroupMemberDetailReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMemberDetailMapper {
    GroupMemberDetail toEntity(GroupMemberDetailReq req);
    GroupMemberDetailDTO toDTO(GroupMemberDetail entity);
}

