package me.passin.loadknife.sample.convertor;

import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.callback.SuccessCallback;
import me.passin.loadknife.core.Convertor;
import me.passin.loadknife.sample.callback.EmptyCallback;
import me.passin.loadknife.sample.callback.ErrorCallback;
import me.passin.loadknife.sample.callback.LoadingCallback;
import me.passin.loadknife.sample.callback.UnknowCallback;
import static me.passin.loadknife.sample.convertor.ViewState.EMPTY;
import static me.passin.loadknife.sample.convertor.ViewState.ERROR;
import static me.passin.loadknife.sample.convertor.ViewState.LOADING;
import static me.passin.loadknife.sample.convertor.ViewState.SUCCESS;
import static me.passin.loadknife.sample.convertor.ViewState.UNKNOWN;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 16:02
 * @desc:
 */
public class StateConvertor implements Convertor<Integer> {

    public static StateConvertor create() {
        return new StateConvertor();
    }

    @Override
    public Class<? extends Callback> convert(@ViewState Integer value) throws Exception {
        if (value == null) {
            return null;
        }
        switch (value) {
            case LOADING:
                return LoadingCallback.class;
            case EMPTY:
                return EmptyCallback.class;
            case ERROR:
                return ErrorCallback.class;
            case SUCCESS:
                return SuccessCallback.class;
            case UNKNOWN:
                return UnknowCallback.class;
            default:
                return null;
        }
    }

}
