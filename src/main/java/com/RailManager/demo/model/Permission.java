package com.RailManager.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Permission {
    private Integer userId;
    private Boolean canAdd;
    private Boolean canDelete;
    private Boolean canUpdate;
    private Boolean canSelect;

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getCanAdd() {
        return canAdd;
    }
    public void setCanAdd(Boolean canAdd) {
        this.canAdd = canAdd;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }
    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Boolean getCanUpdate() {
        return canUpdate;
    }
    public void setCanUpdate(Boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public Boolean getCanSelect() {
        return canSelect;
    }
    public void setCanSelect(Boolean canSelect) {
        this.canSelect = canSelect;
    }
}
