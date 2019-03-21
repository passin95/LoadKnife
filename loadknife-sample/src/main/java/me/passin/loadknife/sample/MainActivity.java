package me.passin.loadknife.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.passin.loadknife.sample.view.AnimateActivity;
import me.passin.loadknife.sample.view.ConvertorActivity;
import me.passin.loadknife.sample.view.LottieActivity;
import me.passin.loadknife.sample.view.PlaceholderActivity;
import me.passin.loadknife.sample.view.SampleActivity;
import me.passin.loadknife.sample.view.SingleFragmentActivity;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 15:55
 * @desc:
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sample(View view) {
        startActivity(new Intent(this, SampleActivity.class));
    }

    public void placeholder(View view) {
        startActivity(new Intent(this, PlaceholderActivity.class));
    }

    public void singleFragment(View view) {
        startActivity(new Intent(this, SingleFragmentActivity.class));
    }

    public void animat(View view) {
        startActivity(new Intent(this, AnimateActivity.class));
    }

    public void lottie(View view) {
        startActivity(new Intent(this, LottieActivity.class));
    }

    public void convertor(View view) {
        startActivity(new Intent(this, ConvertorActivity.class));
    }
}
