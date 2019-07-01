package com.playgilround.schedule.client.hellowolrd.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.playgilround.schedule.client.hellowolrd.databinding.ItemRepoBinding;
import com.playgilround.schedule.client.hellowolrd.util.GithubRepo;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private final List<GithubRepo> repos;

    public RepoAdapter(List<GithubRepo> repos) {
        this.repos = repos;
    }

    public void updateItems(List<GithubRepo> items) {
        this.repos.clear();
        this.repos.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoAdapter.ViewHolder(ItemRepoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RepoAdapter.ViewHolder holder, int position) {
        holder.bind(repos.get(position));
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemRepoBinding binding;

        ViewHolder(ItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(GithubRepo repo) {
            binding.ownerView.setText(repo.owner);
            binding.nameView.setText(repo.name);
            binding.urlView.setText(repo.url);
        }
    }
}
