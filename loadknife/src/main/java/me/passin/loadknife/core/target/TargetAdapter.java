package me.passin.loadknife.core.target;

import me.passin.loadknife.core.LoadKnife;
import me.passin.loadknife.core.LoadLayout;

/**
 * @author: zbb
 * @date: 2020/5/24 10:56
 * @desc:
 */
public interface TargetAdapter {

    LoadLayout adapt(LoadKnife loadKnife, Object target) throws Exception;

}
