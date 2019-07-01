package com.playgilround.schedule.client.hellowolrd;

import android.app.Activity;
import android.os.Bundle;

import com.playgilround.schedule.client.hellowolrd.adapter.RepoAdapter;
import com.playgilround.schedule.client.hellowolrd.databinding.GithubMainBinding;
import com.playgilround.schedule.client.hellowolrd.util.GithubClient;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GithubActivity extends Activity {

    GithubClient github;
    private Disposable disposable;
    private RepoAdapter adapter;

    private static final String OWNER = "c004245";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GithubMainBinding binding = DataBindingUtil.setContentView(this, R.layout.github_main);
        adapter = new RepoAdapter(new ArrayList<>());

        binding.recyclerView.setAdapter(adapter);

        github = new GithubClient();
        disposable = github.getApi().getRepos(OWNER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> adapter.updateItems(items));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

