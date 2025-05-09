package com.practice.mapper;

import com.practice.dto.GroupReportDTO;
import com.practice.entity.Account;
import com.practice.entity.GroupEntity;
import com.practice.entity.GroupReport;
import com.practice.req.GroupReportReq;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-08T16:22:11+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class GroupReportMapperImpl implements GroupReportMapper {

    @Override
    public GroupReportDTO toDTO(GroupReport report) {
        if ( report == null ) {
            return null;
        }

        GroupReportDTO groupReportDTO = new GroupReportDTO();

        groupReportDTO.setGroupId( reportGroupId( report ) );
        groupReportDTO.setSenderName( reportSenderFullName( report ) );
        groupReportDTO.setCreatedAt( report.getCreatedAt() );
        groupReportDTO.setId( report.getId() );
        groupReportDTO.setTitle( report.getTitle() );
        groupReportDTO.setContent( report.getContent() );

        return groupReportDTO;
    }

    @Override
    public GroupReport toEntity(GroupReportReq req) {
        if ( req == null ) {
            return null;
        }

        GroupReport groupReport = new GroupReport();

        groupReport.setGroup( groupReportReqToGroupEntity( req ) );
        groupReport.setSender( groupReportReqToAccount( req ) );
        groupReport.setCreatedAt( req.getCreatedAt() );
        groupReport.setTitle( req.getTitle() );
        groupReport.setContent( req.getContent() );

        return groupReport;
    }

    private Long reportGroupId(GroupReport groupReport) {
        if ( groupReport == null ) {
            return null;
        }
        GroupEntity group = groupReport.getGroup();
        if ( group == null ) {
            return null;
        }
        long id = group.getId();
        return id;
    }

    private String reportSenderFullName(GroupReport groupReport) {
        if ( groupReport == null ) {
            return null;
        }
        Account sender = groupReport.getSender();
        if ( sender == null ) {
            return null;
        }
        String fullName = sender.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }

    protected GroupEntity groupReportReqToGroupEntity(GroupReportReq groupReportReq) {
        if ( groupReportReq == null ) {
            return null;
        }

        GroupEntity groupEntity = new GroupEntity();

        if ( groupReportReq.getGroupId() != null ) {
            groupEntity.setId( groupReportReq.getGroupId() );
        }

        return groupEntity;
    }

    protected Account groupReportReqToAccount(GroupReportReq groupReportReq) {
        if ( groupReportReq == null ) {
            return null;
        }

        Account account = new Account();

        if ( groupReportReq.getSenderId() != null ) {
            account.setId( groupReportReq.getSenderId() );
        }

        return account;
    }
}
