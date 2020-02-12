package com.yechy.dailypic.repository.http;

/**
 * Created by cloud on 2020-02-12.
 */
public class ApiResponse<T> {
    private T data;
    private int returnCode;

    public ApiResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public int getReturnCode() {
        return returnCode;
    }
}
