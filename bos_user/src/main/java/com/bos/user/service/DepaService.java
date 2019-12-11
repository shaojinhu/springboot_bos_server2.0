package com.bos.user.service;

import com.bos.pojo.user.Depa;
import com.bos.response.Result;

public interface DepaService {
    Result getDepa();

    Result addDepa(Depa depa);

    Result updateDepa(Depa depa);

    Result deleteDepa(Depa depa);
}
