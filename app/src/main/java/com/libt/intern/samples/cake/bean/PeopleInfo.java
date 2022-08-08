package com.libt.intern.samples.cake.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Cake on 2018/8/10.
 */

public class PeopleInfo extends LitePalSupport implements Comparable<PeopleInfo>{
    private String name;
    private String password;
    private String headPath;
    private String nickName;
    private String weShowID;
    private String email;
    private String introduction;

    public PeopleInfo() {

    }

    public PeopleInfo(String email, String headPath, String introduction, String name, String nickName, String password, String weShowID) {
        this.email = email;
        this.headPath = headPath;
        this.introduction = introduction;
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.weShowID = weShowID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWeShowID() {
        return weShowID;
    }

    public void setWeShowID(String weShowID) {
        this.weShowID = weShowID;
    }

    @Override
    public int compareTo(PeopleInfo o) {
        if(this.nickName != null && o.nickName != null){
            if(this.nickName.compareTo(o.nickName) != 0){
                return this.nickName.compareTo(o.nickName);
            }
        }else if(this.nickName != null && o.nickName == null){
            if(this.nickName.compareTo(o.name) != 0){
                return this.nickName.compareTo(o.name);
            }
        }else if(this.nickName == null && o.nickName != null){
            if(this.name.compareTo(o.nickName) != 0){
                return this.name.compareTo(o.nickName);
            }
        }
        return this.name.compareTo(o.name);
    }
}
