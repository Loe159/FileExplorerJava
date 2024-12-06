package com.esiea.pootd2.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FolderInode extends Inode {
    protected List<Inode> children;
    protected Integer size;

    public FolderInode(String name) {
        super(name);

        children = new ArrayList<>();

        Random rand = new Random();
        this.size = rand.nextInt(1000000);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public List<Inode> getChildren() {
        return children;
    }

    public void addChild(Inode child) {
        children.add(child);
        child.setParent(this);
    }
}