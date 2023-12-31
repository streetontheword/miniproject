package vttp.ssf.miniproject.model;

public class Dog {

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
    

    public Dog(String name, String imageId, String image) {
        this.name = name;
        this.imageId = imageId;
       
    }
    
    public Dog() {
    }

    @Override
    public String toString() {
        return "Dog [name=" + name + ", imageId=" + imageId + ", image=";
    } 


    

}
