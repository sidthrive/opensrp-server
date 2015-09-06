package org.opensrp.dto;

import java.util.ArrayList;
import java.util.List;

public class LastIdDTO {

    private Integer status;
    private Long lastUsedId;

    public LastIdDTO(){
        this.status = 0;
    }

    public LastIdDTO(Long lastUsedId) {
        this.lastUsedId = lastUsedId;
    }

    public Long getLastUsedId() {
        return lastUsedId;
    }

    public void setLastUsedId(Long lastUsedId) {
        this.lastUsedId = lastUsedId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}