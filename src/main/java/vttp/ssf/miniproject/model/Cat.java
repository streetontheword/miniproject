package vttp.ssf.miniproject.model;

public class Cat {

    private String name;
    private String imageId;


    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    
    public Cat() {
    }
    
    public Cat(String name, String imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    
    @Override
    public String toString() {
        return "Cat [name=" + name + ", imageId=" + imageId + "]";
    }

    
}
