package me.passin.loadknife.core.target;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadLayout;

/**
 * @author: zbb
 * @date: 2020/5/24 11:00
 * @desc:
 */
public class ActivityTargetAdapter implements TargetAdapter {

    private static final ActivityTargetAdapter INSTANCE = new ActivityTargetAdapter();

    public static ActivityTargetAdapter getInstance() {
        return INSTANCE;
    }

    @Override
    public LoadLayout adapt(LoadKnife loadKnife, Object target) throws Exception {
        if (!(target instanceof Activity)) {
            return null;
        }
        Activity activity = (Activity) target;
        ViewGroup parentView = activity.findViewById(android.R.id.content);
        View realView = parentView.getChildAt(0);
        LayoutParams realViewLayoutParams = realView.getLayoutParams();
        parentView.removeViewAt(0);

        LoadLayout mLoadLayout = new LoadLayout(activity, realView, loadKnife);
        realView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mLoadLayout.addView(realView);

        parentView.addView(mLoadLayout, 0, realViewLayoutParams);
        return mLoadLayout;
    }

}
