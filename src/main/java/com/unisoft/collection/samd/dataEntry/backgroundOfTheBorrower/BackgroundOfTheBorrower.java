package com.unisoft.collection.samd.dataEntry.backgroundOfTheBorrower;

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BackgroundOfTheBorrower extends BaseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   @Lob
    private String backgroundOftheBorrower;
    private String accountNumber;
}
