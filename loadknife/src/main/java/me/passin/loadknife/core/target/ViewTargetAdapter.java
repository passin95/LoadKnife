package me.passin.loadknife.core.target;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadLayout;

/**
 * @author: zbb
 * @date: 2020/5/24 14:18
 * @desc:
 */
public class ViewTargetAdapter implements TargetAdapter {

    private static final ViewTargetAdapter INSTANCE = new ViewTargetAdapter();

    public static ViewTargetAdapter getInstance() {
        return INSTANCE;
    }

    private static Class mConstraintLayoutClass;

    static {
        try {
            mConstraintLayoutClass = Class.forName("androidx.constraintlayout.widget.ConstraintLayout");
        } catch (ClassNotFoundException e) {
            try {
                mConstraintLayoutClass = Class.forName("android.support.constraint.ConstraintLayout");
            } catch (ClassNotFoundException ex) {
            }
        }
    }

    @Override
    public LoadLayout adapt(LoadKnife loadKnife, Object target) throws Exception {
        if (!(target instanceof View)) {
            return null;
        }
        View realView = (View) target;
        Context context = realView.getContext();
        LayoutParams realViewLayoutParams = realView.getLayoutParams();
        ViewGroup parentView = (ViewGroup) (realView.getParent());

        int childIndex = 0;
        if (parentView != null) {
            // childIndex 不会返回 -1.
            childIndex = parentView.indexOfChild(realView);
            parentView.removeViewAt(childIndex);
        }

        LoadLayout mLoadLayout = new LoadLayout(context, realView);
        realView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mLoadLayout.addView(realView);

        if (parentView != null) {
            if (loadKnife.isEnableUseChildViewId() && (parentView instanceof RelativeLayout ||
                    (mConstraintLayoutClass != null && mConstraintLayoutClass.isInstance(parentView)))) {
                mLoadLayout.setId(realView.getId());
            }
            parentView.addView(mLoadLayout, childIndex, realViewLayoutParams);
        }
        return mLoadLayout;
    }

}
