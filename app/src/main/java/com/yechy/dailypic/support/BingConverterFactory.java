package com.yechy.dailypic.support;

import com.yechy.dailypic.repository.http.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by cloud on 2020-02-12.
 */
public class BingConverterFactory extends Converter.Factory {

    public static BingConverterFactory create() {
        return new BingConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == ApiResponse.class) {
            return new BingConverter();
        } else {
            return new BingConverter();
        }
    }
}
