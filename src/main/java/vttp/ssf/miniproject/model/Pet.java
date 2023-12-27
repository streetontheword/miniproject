package vttp.ssf.miniproject.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Pet {


    @NotEmpty(message = "name of pet is mandatory")
    @Size(min = 3, max = 30, message = "Name must be between 3 to 30 characters")
    private String name;

    @Size(max =15, message = "number exceeded 15 characters")
    private String microChipNumber;

    @Past(message = "Birth date must be a past date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String gender; 

   
    private String breed;

    private Integer age;

    @Past(message = "Birth date must be a past date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfVaccination;

     @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfNextVaccination;

    private String lastGroomedDate;

    private String nextGroomedDate;

    private String allergies;

    private String comments; 



    public Pet(String name, String microChipNumber, Date dateOfBirth, String breed, Integer age, Date dateOfVaccination, Date dateOfNextVaccination, String lastGroomedDate,String nextGroomedDate,String allergies, String comments) {
        this.name = name;
        this.microChipNumber = microChipNumber;
        this.dateOfBirth = dateOfBirth;
        this.breed = breed;
        this.age = age;
        this.dateOfVaccination = dateOfVaccination;
        this.dateOfNextVaccination = dateOfNextVaccination;
        this.lastGroomedDate = lastGroomedDate;
        this.nextGroomedDate = nextGroomedDate;
        this.allergies = allergies;
        this.comments = comments;
    }


    public Pet() {
    }

    
    public Pet(String comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMicroChipNumber() {
        return microChipNumber;
    }

    public void setMicroChipNumber(String microChipNumber) {
        this.microChipNumber = microChipNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDateOfVaccination() {
        return dateOfVaccination;
    }

    public void setDateOfVaccination(Date dateOfVaccination) {
        this.dateOfVaccination = dateOfVaccination;
    }

    public String getLastGroomedDate() {
        return lastGroomedDate;
    }

    public void setLastGroomedDate(String lastGroomedDate) {
        this.lastGroomedDate = lastGroomedDate;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    

     public Date getDateOfNextVaccination() {
        return dateOfNextVaccination;
    }


    public void setDateOfNextVaccination(Date dateOfNextVaccination) {
        this.dateOfNextVaccination = dateOfNextVaccination;
    }


    public String getNextGroomedDate() {
        return nextGroomedDate;
    }


    public void setNextGroomedDate(String nextGroomedDate) {
        this.nextGroomedDate = nextGroomedDate;
    }


    @Override
    public String toString() {
        return "Pet [name=" + name + ", microChipNumber=" + microChipNumber + ", dateOfBirth=" + dateOfBirth
                + ", breed=" + breed + ", age=" + age + ", dateOfVaccination=" + dateOfVaccination
                + ", lastGroomedDate=" + lastGroomedDate + ", allergies=" + allergies + ", comments=" + comments + "]";
    }
    
}
