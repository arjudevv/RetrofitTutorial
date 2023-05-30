package com.example.apituto.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.apituto.Api.ApiClient;
import com.example.apituto.Api.ApiInterface;
import com.example.apituto.Models.Comments;
import com.example.apituto.R;
import com.example.apituto.databinding.ActivityCommentsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity {

    ActivityCommentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ShowComments();
        setListeners();
    }

    private void setListeners() {
        binding.buttonPosts.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }

    private void ShowComments() {

        ApiInterface apiInterface;

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<List<Comments>> call = apiInterface.getComments(5);

        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {

                if (!response.isSuccessful()) {
                    binding.textResults.setText("Code: " + response.code());
                    return;
                }

                List<Comments> comment = response.body();

                for (Comments comments : comment) {
                    String content = "";

                    content += "ID: " + comments.getId() + "\n";
                    content += "Post ID: " + comments.getPostId() + "\n";
                    content += "Name: " + comments.getName() + "\n";
                    content += "Email: " + comments.getEmail() + "\n";
                    content += "Text: " + comments.getText() + "\n\n";

                    binding.textResults.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                binding.textResults.setText(t.getMessage());
            }
        });
    }
}