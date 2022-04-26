/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gt.interpackage.administration.pk;

import com.gt.interpackage.administration.model.Checkpoint;
import com.gt.interpackage.administration.model.Package;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author bryan
 */
public class PKPackageCheckpoint implements Serializable {

    private Checkpoint checkpoint;
    private Package packages;

    public PKPackageCheckpoint() { }
    
    public PKPackageCheckpoint(Checkpoint checkpoint, Package packages) {
        this.checkpoint = checkpoint;
        this.packages = packages;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.checkpoint);
        hash = 59 * hash + Objects.hashCode(this.packages);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PKPackageCheckpoint other = (PKPackageCheckpoint) obj;
        if (!Objects.equals(this.checkpoint, other.checkpoint)) {
            return false;
        }
        return Objects.equals(this.packages, other.packages);
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoint = checkpoint;
    }

    public Package getPackages() {
        return packages;
    }

    public void setPackages(Package packages) {
        this.packages = packages;
    }
    
}
