package me.passin.loadknife.sample.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import me.passin.loadknife.sample.R;

public class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_content, NormalFragment.newInstance()).commit();
    }
}
