package me.passin.loadknife.sample.callback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.core.ViewHelper;
import me.passin.loadknife.sample.R;

public class AnimateCallback extends Callback {

    @NonNull
    @Override
    public View onCreateView(Context context, @Nullable ViewGroup container) {
        return LayoutInflater.from(context).inflate(R.layout.callback_animate, container, false);
    }

    @Override
    public void onAttach(Context context, ViewHelper viewHelper) {
        View animateView = viewHelper.getView(R.id.view_animate);
        Animation animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(Integer.MAX_VALUE);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animateView.startAnimation(animation);
        Toast.makeText(context.getApplicationContext(), "start animation", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach(Context context, ViewHelper viewHelper) {
        View animateView = viewHelper.getView(R.id.view_animate);
        if (animateView != null) {
            animateView.clearAnimation();
            Toast.makeText(context.getApplicationContext(), "stop animation", Toast.LENGTH_SHORT).show();
        }
    }

}
