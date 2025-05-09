package com.practice.mapper;

import com.practice.dto.GroupReportDTO;
import com.practice.entity.GroupReport;
import com.practice.req.GroupReportReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupReportMapper {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "sender.fullName", target = "senderName")  // Lấy tên người gửi từ sender
    @Mapping(source = "createdAt", target = "createdAt")
    GroupReportDTO toDTO(GroupReport report);

    @Mapping(target = "group.id", source = "groupId")
    @Mapping(target = "sender.id", source = "senderId") // Ánh xạ senderId vào sender
    @Mapping(target = "createdAt", source = "createdAt")
    GroupReport toEntity(GroupReportReq req);
}

