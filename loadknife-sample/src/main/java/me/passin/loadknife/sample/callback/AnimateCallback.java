package me.passin.loadknife.sample.callback;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;
import me.passin.loadknife.core.ViewHelper;
import me.passin.loadknife.sample.R;

public class AnimateCallback extends EmptyCallback {

    private View animateView;

    @Override
    public int getLayoutId() {
        return R.layout.callback_animate;
    }

    @Override
    public void onAttach(Context context, ViewHelper viewHelper) {
        animateView = viewHelper.getView(R.id.view_animate);
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
        if (animateView != null) {
            animateView.clearAnimation();
        }
        Toast.makeText(context.getApplicationContext(), "stop animation", Toast.LENGTH_SHORT).show();
    }

}
