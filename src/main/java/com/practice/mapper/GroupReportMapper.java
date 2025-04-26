package com.practice.mapper;

import com.practice.dto.GroupReportDTO;
import com.practice.entity.GroupReport;
import com.practice.req.GroupReportReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupReportMapper {
    public GroupReport toEntity(GroupReportReq groupReportReq);   // THÊM public
    public GroupReportDTO toDTO(GroupReport groupReport);   // THÊM public
}
