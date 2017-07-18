package thozhilali.com.thozhilali;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class UserHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_workerprofile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.search:
                Intent in=new Intent(getApplicationContext(),Home.class);
                in.putExtra("login","login");
                startActivity(in);
                break;
            case R.id.profile:
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

