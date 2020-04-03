package me.passin.loadknife.sample.convertor;

import android.support.annotation.IntDef;
import static me.passin.loadknife.sample.convertor.ViewState.EMPTY;
import static me.passin.loadknife.sample.convertor.ViewState.ERROR;
import static me.passin.loadknife.sample.convertor.ViewState.LOADING;
import static me.passin.loadknife.sample.convertor.ViewState.SUCCESS;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 16:09
 * @desc:
 */
@IntDef({SUCCESS, EMPTY, ERROR, LOADING})
public @interface ViewState {

    int SUCCESS = 1;
    int EMPTY = 2;
    int ERROR = 3;
    int LOADING = 4;
    int UNKNOWN = 5;

}