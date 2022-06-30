package com.example.seed.vo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="tm_usr")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class UserVo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usrSeq;
    private String usrId;
    private String usrName;
    private String usrPwd;

//    @Builder
//    public UserVo(String usrId, String usrName, String usrPwd) {
//        this.usrId = usrId;
//        this.usrName = usrName;
//        this.usrPwd = usrPwd;
//    }
}
