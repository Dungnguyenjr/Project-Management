package com.practice.dto;

import lombok.Data;

@Data
public class CriteriaDTO {
    private Integer id;
    private String criteriaName;

    public CriteriaDTO(Integer id, String criteriaName) {
        this.id = id;
        this.criteriaName = criteriaName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }
}
