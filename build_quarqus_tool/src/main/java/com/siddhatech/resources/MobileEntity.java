package com.siddhatech.resources;

public class MobileEntity {

    int id;
    String mobileName;
    String brand;
    int ram;
    String externalStorage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobileName() {
        return mobileName;
    }

    public void setMobileName(String mobileName) {
        this.mobileName = mobileName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getExternalStorage() {
        return externalStorage;
    }

    public void setExternalStorage(String externalStorage) {
        this.externalStorage = externalStorage;
    }

    @Override
    public String toString() {
        return "MobileEntity [id=" + id + ", mobileName=" + mobileName + ", brand=" + brand + ", ram=" + ram + ", externalStorage=" + externalStorage + "]";
    }

    public MobileEntity(int id, String mobileName, String brand, int ram, String externalStorage) {
        super();
        this.id = id;
        this.mobileName = mobileName;
        this.brand = brand;
        this.ram = ram;
        this.externalStorage = externalStorage;
    }

    public MobileEntity() {
    }


}
