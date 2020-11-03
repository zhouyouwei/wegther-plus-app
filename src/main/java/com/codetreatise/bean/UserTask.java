package com.codetreatise.bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserTask {

    private StringProperty colId;

    private StringProperty colTradeNo;

    private StringProperty colItemCount;

    private StringProperty colCollectDate;

    public UserTask(String colId, String colTradeNo, String colItemCount, String colCollectDate) {
        this.colId = new SimpleStringProperty(colId);
        this.colTradeNo = new SimpleStringProperty(colTradeNo);
        this.colItemCount = new SimpleStringProperty(colItemCount);
        this.colCollectDate = new SimpleStringProperty(colCollectDate);
    }

    public StringProperty getColId() {
        return colId;
    }

    public void setColId(String colId) {
        this.colId.set(colId);
    }

    public StringProperty getColTradeNo() {
        return colTradeNo;
    }

    public void setColTradeNo(String colTradeNo) {
        this.colTradeNo.set(colTradeNo);
    }

    public StringProperty getColItemCount() {
        return colItemCount;
    }

    public void setColItemCount(String colItemCount) {
        this.colItemCount.set(colItemCount);
    }

    public StringProperty getColCollectDate() {
        return colCollectDate;
    }

    public void setColCollectDate(String colCollectDate) {
        this.colCollectDate.set(colCollectDate);
    }

    @Override
    public String toString() {
        return "UserTask{" +
                "colId=" + colId +
                ", colTradeNo=" + colTradeNo +
                ", colItemCount=" + colItemCount +
                ", colCollectDate=" + colCollectDate +
                '}';
    }
}
