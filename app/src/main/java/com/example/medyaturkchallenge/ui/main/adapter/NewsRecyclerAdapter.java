package com.example.medyaturkchallenge.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medyaturkchallenge.data.remote.models.ItemList;
import com.example.medyaturkchallenge.databinding.RecInMainNewsBinding;
import com.example.medyaturkchallenge.ui.main.MainNavigator;

import java.util.List;

/**
 * sectionType'ı NEWS olan haberlerin yönetildiği adapter
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

   private final List<ItemList> itemLists;
   private final MainNavigator mainNavigator;

   public NewsRecyclerAdapter(List<ItemList> itemLists, MainNavigator mainNavigator) {
      this.itemLists = itemLists;
      this.mainNavigator = mainNavigator;
   }

   @NonNull
   @Override
   public NewsRecyclerAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      RecInMainNewsBinding binding = RecInMainNewsBinding.inflate(layoutInflater, parent, false);
      return new NewsRecyclerAdapter.NewsViewHolder(binding, mainNavigator);
   }

   @Override
   public void onBindViewHolder(@NonNull NewsRecyclerAdapter.NewsViewHolder holder, int position) {
      ItemList item = itemLists.get(position);
      holder.bind(item);
   }

   @Override
   public int getItemCount() {
      return itemLists.size();
   }

   static class NewsViewHolder extends RecyclerView.ViewHolder {
      // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
      // Since our layout file is rec_in_main_news.xml, our auto generated binding class is RecInNewsBinding
      RecInMainNewsBinding binding;
      MainNavigator mainNavigator;

      public NewsViewHolder(RecInMainNewsBinding binding, MainNavigator mainNavigator) {
         super(binding.getRoot());
         this.binding = binding;
         this.mainNavigator = mainNavigator;
      }

      public void bind(ItemList itemList) {
         binding.setItemList(itemList);
         binding.executePendingBindings();
         binding.setMainNavigator(mainNavigator);
      }
   }
}