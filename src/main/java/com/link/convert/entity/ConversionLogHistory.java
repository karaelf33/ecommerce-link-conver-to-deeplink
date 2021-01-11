package com.link.convert.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CONVERSION_LOG_HISTORY")
@Data
public class ConversionLogHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONVERSION_LOG_HISTORY_ID")
    private int conversionLogHistoryId;

    @Column(name = "REQEUST_HEADER")
    private String requestHeader;

    @Column(name = "RESPONSE_BODY")
    private String responseBody;

    @Column(name = "CONVERSION_DATE")
    private Date conversionDate;


}
