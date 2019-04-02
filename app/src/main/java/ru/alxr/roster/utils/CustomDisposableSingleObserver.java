package ru.alxr.roster.utils;

import io.reactivex.observers.DisposableSingleObserver;

public class CustomDisposableSingleObserver<T> extends DisposableSingleObserver<T> {

    public CustomDisposableSingleObserver(OnSuccess<T> successCallback, OnError errorCallback) {
        mSuccessCallback = successCallback;
        mErrorCallback = errorCallback;
    }

    private final OnSuccess<T> mSuccessCallback;
    private final OnError mErrorCallback;

    @Override
    public void onSuccess(T t) {
        mSuccessCallback.onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        mErrorCallback.onError(e);
    }

    public interface OnSuccess<T> {
        void onSuccess(T t);
    }

    public interface OnError {
        void onError(Throwable e);
    }

}