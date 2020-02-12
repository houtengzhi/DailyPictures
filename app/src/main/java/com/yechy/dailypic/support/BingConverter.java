package com.yechy.dailypic.support;

import com.yechy.dailypic.repository.PictureInfo;
import com.yechy.dailypic.repository.http.ApiResponse;
import com.yechy.dailypic.util.DataParser;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by cloud on 2020-02-12.
 */
public class BingConverter implements Converter<ResponseBody, ApiResponse<List<PictureInfo>>> {

    @Override
    public ApiResponse<List<PictureInfo>> convert(ResponseBody value) throws IOException {
        String response = value.string();
        List<PictureInfo> data = DataParser.INSTANCE.parseBingData(response);
        ApiResponse<List<PictureInfo>> apiResponse = new ApiResponse<>(data);
        return apiResponse;
    }
}
