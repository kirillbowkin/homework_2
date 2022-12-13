package com.company.homeworkloans.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum LoanStatus implements EnumClass<Integer> {

    REQUESTED(10),
    APPROVED(20),
    REJECTED(30);

    private Integer id;

    LoanStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static LoanStatus fromId(Integer id) {
        for (LoanStatus at : LoanStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}