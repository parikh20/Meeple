
package com.example.jaineek.meeplemain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jaineek.meeplemain.model.Post;

public class ViewPostActivity extends AppCompatActivity {

    public static final String KEY_EXTRA_POST = "com.example.jaineek.meeplemain.extra_tag_post";

    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        Intent intent = getIntent();
        mPost = intent.getParcelableExtra(KEY_EXTRA_POST);

        Toast.makeText(ViewPostActivity.this, mPost.eventTitle + ": " +
                mPost.eventDesc, Toast.LENGTH_SHORT).show();
    }
}