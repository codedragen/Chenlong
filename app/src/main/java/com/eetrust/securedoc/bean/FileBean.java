package com.eetrust.securedoc.bean;

import java.util.List;

/**
 * Created by eetrust on 16/6/12.
 */
public class FileBean {

    public int id;
    public int pid;
    public String name;
    public FileBean parent;
    public List<FileBean> children;
    public boolean isFile;
    public String sender;
    public boolean isChecked;



}
