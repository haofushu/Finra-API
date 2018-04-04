package com.example.demo.Entity;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "File")

@NamedNativeQuery(
        name    =   "File.getRecent",
        query   =   "SELECT * FROM File WHERE DATEDIFF(MINUTE,File.Date ,SYSDATE)<60 ",
        resultClass=File.class
)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private Date date;
    @Column(name = "path")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File(){}

    public File(Integer id,String name, Date date, String path) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.path = path;
    }

    public File(String name, Date date, String path) {
        this.name = name;
        this.date = date;
        this.path = path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
