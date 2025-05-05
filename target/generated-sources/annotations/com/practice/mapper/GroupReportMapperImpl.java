package com.practice.mapper;

import com.practice.dto.GroupReportDTO;
import com.practice.entity.GroupReport;
import com.practice.req.GroupReportReq;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T05:04:42+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class GroupReportMapperImpl implements GroupReportMapper {

    @Override
    public GroupReport toEntity(GroupReportReq groupReportReq) {
        if ( groupReportReq == null ) {
            return null;
        }

        GroupReport groupReport = new GroupReport();

        groupReport.setTitle( groupReportReq.getTitle() );
        groupReport.setContent( groupReportReq.getContent() );

        return groupReport;
    }

    @Override
    public GroupReportDTO toDTO(GroupReport groupReport) {
        if ( groupReport == null ) {
            return null;
        }

        GroupReportDTO groupReportDTO = new GroupReportDTO();

        groupReportDTO.setId( groupReport.getId() );
        groupReportDTO.setTitle( groupReport.getTitle() );
        groupReportDTO.setContent( groupReport.getContent() );
        groupReportDTO.setCreatedAt( groupReport.getCreatedAt() );

        return groupReportDTO;
    }
}
