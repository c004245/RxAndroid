package com.playgilround.schedule.client.hellowolrd;

import android.app.Activity;
import android.os.Bundle;

import com.playgilround.schedule.client.hellowolrd.databinding.GithubMainBinding;
import com.playgilround.schedule.client.hellowolrd.util.GithubClient;

import androidx.databinding.DataBindingUtil;
import io.reactivex.disposables.Disposable;

public class GithubActivity extends Activity {

    GithubClient github;
    private Disposable disposable;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GithubMainBinding binding = DataBindingUtil.setContentView(this, R.layout.github_main);
        github = new GithubClient();
        disposable = github.getApi().getRepos(OWER)
    }
}

