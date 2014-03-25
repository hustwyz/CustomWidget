
package hust.wyz.widget;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    ProgressBar mProgressBar; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = (ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setMax(100);
        mProgressBar.animatorProgress(50);
    }

}
