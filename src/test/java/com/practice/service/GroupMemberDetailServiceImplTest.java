package com.practice.service;

import com.practice.dto.GroupMemberDetailDTO;
import com.practice.entity.*;
import com.practice.enums.EnumRoleMember;
import com.practice.repository.AccountRepository;
import com.practice.repository.GroupMemberDetailRepository;
import com.practice.repository.GroupRepository;
import com.practice.req.GroupMemberDetailReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupMemberDetailServiceImplTest {

    @InjectMocks
    private GroupMemberDetailServiceImpl groupMemberDetailService;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private GroupMemberDetailRepository groupMemberDetailRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdateDetail_NewMember() {
        GroupMemberDetailReq req = new GroupMemberDetailReq();
        req.setGroupId(1);
        req.setStudentId(2);
        req.setGroupScore(8.5f);
        req.setPersonalScore(9f);
        req.setIndividualReport("report.pdf");

        GroupEntity group = new GroupEntity();
        group.setId(1L);
        group.setGroupName("Group A");

        Account student = new Account();
        student.setId(2);
        student.setFullName("Nguyen Van A");
        student.setStudentCode("SV001");
        student.setEnumRoleMember(EnumRoleMember.LEADER);

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(accountRepository.findById(2)).thenReturn(Optional.of(student));
        when(groupMemberDetailRepository.findByGroupAndStudent(group, student)).thenReturn(Optional.empty());

        GroupMemberDetail savedDetail = new GroupMemberDetail();
        savedDetail.setId(99L);
        savedDetail.setGroup(group);
        savedDetail.setStudent(student);
        savedDetail.setGroupScore(8.5f);
        savedDetail.setPersonalScore(9f);
        savedDetail.setIndividualReport("report.pdf");

        when(groupMemberDetailRepository.save(any())).thenReturn(savedDetail);

        GroupMemberDetailDTO result = groupMemberDetailService.addOrUpdateDetail(req);

        assertNotNull(result);
        assertEquals(99L, result.getId());
        assertEquals("Group A", result.getGroupName());
        assertEquals("Nguyen Van A", result.getStudentName());
        assertEquals("SV001", result.getStudentCode());
        assertEquals("LEADER", result.getRoleMember());
    }
}
