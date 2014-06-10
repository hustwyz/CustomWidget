
package hust.wyz.activity;

import android.app.Activity;
import android.os.Bundle;

import hust.wyz.widget.ProgressBar;
import hust.wyz.widget.R;

public class SampleProgressbarActivity extends Activity {

    ProgressBar mProgressBar1; 
    
    ProgressBar mProgressBar2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progresbar);
        mProgressBar1 = (ProgressBar)findViewById(R.id.progressbar);
        mProgressBar1.setMax(100);
        mProgressBar1.animatorProgress(50);
        mProgressBar2 = (ProgressBar)findViewById(R.id.progressbar1);
        mProgressBar2.setMax(150);
        mProgressBar2.animatorProgress(50);
    }

}
