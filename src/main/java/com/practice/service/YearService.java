package com.practice.service;

import com.practice.dto.YearDTO;
import com.practice.req.YearCreateReq;
import com.practice.req.YearUpdateReq;

public interface YearService {

    YearDTO createYear (YearCreateReq yearCreateReq);
    YearDTO updateYear(Long id, YearUpdateReq yearUpdateReq);
    void deleteYear(Long id);
}
