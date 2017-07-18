package thozhilali.com.thozhilali;

/**
 * Created by SEMI on 4/26/2017.
 */

public class WorkerDetails {
    private String name,skill,phone,mail,address,street,city,pincode,age,experience,wage;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public WorkerDetails() {

    }

    public WorkerDetails(String name, String skill, String phone, String mail, String address, String street, String city, String pincode, String age) {

        this.name = name;
        this.skill = skill;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.street = street;
        this.city = city;
        this.pincode = pincode;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
