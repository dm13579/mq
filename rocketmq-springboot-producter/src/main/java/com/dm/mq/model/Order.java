package com.dm.mq.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
  * @className Order 
  * @description 订单
  * @author dm
  * @date 2020/8/17
  * @since JDK1.8
  */
@AllArgsConstructor
@Data
public class Order {

    private long orderid;

    private BigDecimal paymoney;
}

