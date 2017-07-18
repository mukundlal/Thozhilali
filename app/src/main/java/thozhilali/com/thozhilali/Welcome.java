package thozhilali.com.thozhilali;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class Welcome extends AppCompatActivity {
    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        p=(ProgressBar)findViewById(R.id.pg1) ;
        p.setProgress(0);
        Timer t= new  Timer();
        t.schedule(new TimerTask(){
            public void run() {
                Welcome.this.runOnUiThread(new Runnable() {
                    public void run() {
                        p.setProgress(100);
                        Intent i=new Intent(Welcome.this,Home.class);
                        i.putExtra("login","not");
                        startActivity(i);
                        finish();
                    }
                });
            }
        }, 2000);

    }
}
