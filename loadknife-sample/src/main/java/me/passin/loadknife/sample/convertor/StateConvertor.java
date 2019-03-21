package me.passin.loadknife.sample.convertor;

import me.passin.loadknife.callback.Callback;
import me.passin.loadknife.sample.callback.EmptyCallback;
import me.passin.loadknife.sample.callback.ErrorCallback;
import me.passin.loadknife.callback.SuccessCallback;
import me.passin.loadknife.sample.callback.UnknowCallback;
import me.passin.loadknife.core.Convertor;

/**
 * @author: zbb 33775
 * @date: 2019/3/19 16:02
 * @desc:
 */
public class StateConvertor implements Convertor<State> {

    public static StateConvertor create() {
        return new StateConvertor();
    }

    @Override
    public Class<? extends Callback> convert(State value) throws Exception {
        if (value == null) {
            return null;
        }
        switch (value) {
            case SUCCESS:
                return SuccessCallback.class;
            case EMPTY:
                return EmptyCallback.class;
            case ERROR:
                return ErrorCallback.class;
            case UNKNOWN:
                return UnknowCallback.class;
            default:
                return null;
        }
    }
}
