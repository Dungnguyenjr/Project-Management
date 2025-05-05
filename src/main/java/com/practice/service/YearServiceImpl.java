package com.practice.service;

import com.practice.dto.CriteriaDTO;
import com.practice.dto.FieldDTO;
import com.practice.dto.ProjectDTO;
import com.practice.dto.YearDTO;
import com.practice.entity.Field;
import com.practice.entity.Project;
import com.practice.entity.Year;
import com.practice.repository.ProjectRepository;
import com.practice.repository.YearRepository;
import com.practice.req.YearCreateReq;
import com.practice.req.YearUpdateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YearServiceImpl implements YearService{

    @Autowired
    private YearRepository yearRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Override
    public YearDTO createYear(YearCreateReq req) {
        Year year = new Year();
        year.setYear(req.getYear());
        year = yearRepo.save(year);

        // Lấy tất cả các dự án bằng cách sử dụng findAllById
        List<Project> projects = projectRepo.findAllById(req.getProject());

        for (Project p : projects) {
            // Cập nhật mối quan hệ giữa dự án và năm
            p.setYear(year);
        }

        projectRepo.saveAll(projects); // Lưu tất cả các dự án với mối quan hệ năm mới

        // Chuyển thành DTO
        YearDTO dto = new YearDTO();
        dto.setId(year.getId());
        dto.setYear(year.getYear());
        dto.setNumberProject(projects.size());

        // Chuyển các dự án thành DTO và đảm bảo đầy đủ thông tin
        dto.setProjects(projects.stream().map(this::convertToDto).toList());

        return dto;
    }

    @Override
    public YearDTO updateYear(Long id, YearUpdateReq req) {
        Year year = yearRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Year not found"));

        year.setYear(req.getYear());
        Year updatedYear = yearRepo.save(year);

        // Lấy tất cả các dự án mới để cập nhật
        List<Project> projects = projectRepo.findAllById(req.getProject());

        for (Project p : projects) {
            p.setYear(year); // Cập nhật lại mối quan hệ năm
        }

        projectRepo.saveAll(projects); // Lưu lại các dự án với mối quan hệ năm mới

        // Chuyển thành DTO
        YearDTO dto = new YearDTO();
        dto.setId(updatedYear.getId());
        dto.setYear(updatedYear.getYear());
        dto.setNumberProject(projects.size());
        dto.setProjects(projects.stream().map(this::convertToDto).toList());

        return dto;
    }



    private ProjectDTO convertToDto(Project p) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(p.getId());
        dto.setProjectName(p.getProjectName());
        dto.setDescription(p.getDescription());
        dto.setContent(p.getContent());
        dto.setStatus(p.getStatus());

        if (p.getCriteria() != null) {
            dto.setCriteria(p.getCriteria().stream().map(c -> {
                CriteriaDTO criteriaDTO = new CriteriaDTO();
                criteriaDTO.setId(c.getId());
                criteriaDTO.setCriteriaName(c.getCriteriaName());
                return criteriaDTO;
            }).toList());
        } else {
            dto.setCriteria(new ArrayList<>());
        }

        return dto;
    }




    @Override
    public void deleteYear(Long id) {
        yearRepo.deleteById(id);
    }

}
