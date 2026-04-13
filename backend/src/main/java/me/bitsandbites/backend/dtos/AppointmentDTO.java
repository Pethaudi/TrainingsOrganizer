package me.bitsandbites.backend.dtos;

import java.sql.Date;
import java.util.Optional;

public class AppointmentDTO {
    private Optional<Integer> id;
    private Integer relationId;
    private Date date;
    private String note;

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
