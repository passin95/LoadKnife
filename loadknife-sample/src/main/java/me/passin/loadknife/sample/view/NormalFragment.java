package me.passin.loadknife.sample.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.Callback.OnReloadListener;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadService;
import me.passin.loadknife.sample.PostUtil;
import me.passin.loadknife.sample.R;
import me.passin.loadknife.sample.callback.CustomCallback;
import me.passin.loadknife.sample.callback.LoadingCallback;

public class NormalFragment extends Fragment {

    private LoadService loadService;
    private View rootView;

    public static NormalFragment newInstance() {
        Bundle args = new Bundle();
        NormalFragment fragment = new NormalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        rootView = View.inflate(getActivity(), R.layout.fragment_a_content, null);
        LoadKnife loadKnife = new LoadKnife.Builder()
                .defaultCallback(LoadingCallback.class)
                .build();
        loadService = loadKnife.register(rootView, new OnReloadListener() {
            @Override
            public void onReload(Callback callback, View v) {
                // 重新加载逻辑
            }
        });
        loadService.showDefault();
        return loadService.getLoadLayout();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PostUtil.postCallbackDelayed(loadService, CustomCallback.class);
    }

}