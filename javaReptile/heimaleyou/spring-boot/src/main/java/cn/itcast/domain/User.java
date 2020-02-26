package cn.itcast.domain;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tb_user")
@Data
public class User {
    @Id
    /*@GeneratedValue(strategy = GenerationType.IDENTITY)*/
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String userName;
    private String password;
    private String phone;
    private Date created;
    private String salt;
    @Transient
    private String note;
}
