package me.passin.loadknife.core.target;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadLayout;

/**
 * @author: zbb
 * @date: 2020/5/24 10:56
 * @desc:
 */
public interface TargetAdapter {

    @Nullable
    LoadLayout adapt(@NonNull LoadKnife loadKnife, @NonNull Object target) throws Exception;

}
