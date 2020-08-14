package com.dm.rabbitmqspringbootconsumer.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {

    private String orderNo;

    private Date createDt;

    private double payMoney;

    private String userName;
}
