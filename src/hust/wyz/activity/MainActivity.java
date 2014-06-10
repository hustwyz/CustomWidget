package hust.wyz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import hust.wyz.widget.R;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onProgressbarClick(View v){
        Intent intent = new Intent(this, SampleProgressbarActivity.class);
        startActivity(intent);
    }
    
    public void onSwipeMenuClick(View v){
        Intent intent = new Intent(this, SampleSwipeMenuActivity.class);
        startActivity(intent);
    }

}