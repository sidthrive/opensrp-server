package org.opensrp.dto;

import java.util.ArrayList;
import java.util.List;

public class LastIdDTO {


    private Integer status;

    public LastIdDTO(){
        this.status = 0;
    }

    public LastIdDTO(int status) {
        this.status = status;
    }

    public int getIds() {
        return status;
    }

    public void setIds(int status) {
        this.status = status;
    }
}