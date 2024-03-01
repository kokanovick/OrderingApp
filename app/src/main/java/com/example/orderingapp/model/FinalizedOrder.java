package com.example.orderingapp.model;

import java.util.List;

public class FinalizedOrder {
    private Long id;
    private List<TableInfo> tableInfoList;
    private String comment;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TableInfo> getTableInfoList() {
        return tableInfoList;
    }

    public void setTableInfoList(List<TableInfo> tableInfoList) {
        this.tableInfoList = tableInfoList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FinalizedOrder{" +
                "id=" + id +
                ", tableInfoList=" + tableInfoList +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
