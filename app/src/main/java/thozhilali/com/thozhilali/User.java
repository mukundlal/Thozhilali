package thozhilali.com.thozhilali;

/**
 * Created by Nandu on 09-05-2017.
 */

public class User {
    String id;
    String name;
    String phone;
    String email;
    String password;
    public  User(){

    }

    public User(String id,String name, String phone, String email, String password) {
        this.id =id;
        this.name = name;

        this.phone = phone;
        this.email = email;
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
