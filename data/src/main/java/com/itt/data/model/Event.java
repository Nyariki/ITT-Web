/*
 * Copyright (c) 2020. Robert Mayore
 */

package com.itt.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * events
 * @author Robert Mayore
 */
public class Event implements Serializable {
    private BigDecimal id;

    private Long programType;

    private String color;

    private String message;

    private Date programTime;

    private Date createdAt;

    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Long getProgramType() {
        return programType;
    }

    public void setProgramType(Long programType) {
        this.programType = programType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getProgramTime() {
        return programTime;
    }

    public void setProgramTime(Date programTime) {
        this.programTime = programTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}