package com.practice.mapper;

import com.practice.dto.GroupMemberDetailDTO;
import com.practice.entity.GroupMemberDetail;
import com.practice.req.GroupMemberDetailReq;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T12:38:40+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class GroupMemberDetailMapperImpl implements GroupMemberDetailMapper {

    @Override
    public GroupMemberDetail toEntity(GroupMemberDetailReq req) {
        if ( req == null ) {
            return null;
        }

        GroupMemberDetail groupMemberDetail = new GroupMemberDetail();

        groupMemberDetail.setIndividualReport( req.getIndividualReport() );
        groupMemberDetail.setPersonalScore( req.getPersonalScore() );
        groupMemberDetail.setGroupScore( req.getGroupScore() );

        return groupMemberDetail;
    }

    @Override
    public GroupMemberDetailDTO toDTO(GroupMemberDetail entity) {
        if ( entity == null ) {
            return null;
        }

        GroupMemberDetailDTO groupMemberDetailDTO = new GroupMemberDetailDTO();

        groupMemberDetailDTO.setId( entity.getId() );
        groupMemberDetailDTO.setIndividualReport( entity.getIndividualReport() );
        groupMemberDetailDTO.setPersonalScore( entity.getPersonalScore() );
        groupMemberDetailDTO.setGroupScore( entity.getGroupScore() );

        return groupMemberDetailDTO;
    }
}
