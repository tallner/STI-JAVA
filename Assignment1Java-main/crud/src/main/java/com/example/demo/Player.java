package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Player {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
      }
    
      public void setId(Integer id) {
        this.id = id;
      }
    
  
    private String namn;
    private int age;
    private int jersey;
    private String born;

    public void setBorn(String s)
    {
        born  = s;
    }
    public String getBorn()
    {
        return born;
    }


    public void setName(String v)
    {
        namn = v;
    }

    public void setAge(int v)
    {
        age  = v;
    }
    public void setJersey(int v)
    {
        jersey  = v;
    }


    public String getName()
    {
        return namn;
    }
    public int getAge()
    {
        return age;
    }
    public int getJersey()
    {
        return jersey;
    }
}
