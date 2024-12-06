package com.esiea.pootd2.models;

import java.util.Random;

public class FileInode extends Inode {
    protected Integer size;

    public FileInode(String name) {
        super(name);

        Random rand = new Random();
        this.size = rand.nextInt(1000000);
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
