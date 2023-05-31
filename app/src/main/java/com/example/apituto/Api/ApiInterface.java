package com.example.apituto.Api;

import com.example.apituto.Models.Comments;
import com.example.apituto.Models.Photos;
import com.example.apituto.Models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPosts(
            @QueryMap Map<String,String> map
    );

    @GET("posts/{id}/comments")
    Call<List<Comments>> getComments(@Path("id")int postId);

    @GET
    Call<List<Comments>> getComments(@Url String url);

    @POST("posts")
    Call<Post> createPost(@Body Post posts);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String subject
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> map);

    @Headers("Static-Header: 369")
    @PUT("posts/{id}")
    Call<Post> putPost(@Header ("Dynamic-Header") String header,
                       @Path("id") int id,
                       @Body Post post
    );

    @PATCH("posts/{id}")
    Call<Post> patchPost(
            @HeaderMap Map<String, String> headers,
            @Path("id") int id,
            @Body Post post
    );

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

    @GET("photos")
    Call<List<Photos>> getPhotos();


}
