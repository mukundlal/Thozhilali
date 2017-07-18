package thozhilali.com.thozhilali;

/**
 * Created by Nandu on 09-05-2017.
 */

public class Worker {
    String id,fullName, mobile, username, password,selected,location,wage;

    public Worker() {
    }

    public Worker( String id,String fullName, String mobile, String username, String password, String selected, String location, String wage) {
        this.id= id;
        this.fullName = fullName;
        this.mobile = mobile;
        this.username = username;
        this.password = password;

        this.selected = selected;
        this.location = location;
        this.wage = wage;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

       public String getSelected() {
        return selected;
    }

    public String getLocation() {
        return location;
    }

    public String getWage() {
        return wage;
    }
}
