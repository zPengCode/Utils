package utils.http;

import exception.NetWorkException;
import utils.http.callback.NetWorkCallback;

import java.io.IOException;
import java.io.InputStream;

public class BaseNetWorkCallBack implements NetWorkCallback {

    @Override
    public void onSuccess(InputStream inputStream) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        System.out.println("响应失败 ==> code = " + code + " msg = "+ msg);
    }

    @Override
    public void onError(Exception e){
    }

    @Override
    public void onCancel() {
    }
}
