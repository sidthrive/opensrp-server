package org.opensrp.dto;

import java.util.ArrayList;
import java.util.List;

public class LastIdDTO {

    private Integer status;
    private Long id;

    public LastIdDTO(){
        this.status = 0;
    }

    public LastIdDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}