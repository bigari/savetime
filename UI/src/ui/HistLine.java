/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author Black_Shadow
 */
public class HistLine {
    private String code;
    private String details;
    private Double total;
    private String delivree;

    public HistLine(String code, String details, Double total, String delivree) {
        this.code = code;
        this.details = details;
        this.total = total;
        this.delivree = delivree;
    }
    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDelivree() {
        return delivree;
    }

    public void setDelivree(String delivree) {
        this.delivree = delivree;
    }
    
    
    
}
