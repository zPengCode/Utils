package utils.http.callback;

import exception.NetWorkException;

import java.io.InputStream;

public interface NetWorkCallback {

    void onSuccess(InputStream inputStream);

    void onFailure(int code, String msg);

    void onError(Exception e);

    void onCancel();
}