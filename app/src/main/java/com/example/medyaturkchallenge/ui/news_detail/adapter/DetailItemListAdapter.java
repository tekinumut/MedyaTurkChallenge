package com.example.medyaturkchallenge.ui.news_detail.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medyaturkchallenge.data.remote.models.ItemListDetail;
import com.example.medyaturkchallenge.databinding.RecInNewsDetailItemListBinding;
import com.example.medyaturkchallenge.ui.news_detail.NewsDetailNavigator;

import java.util.List;

/**
 * sectionType'ı NEWS olan haberlerin yönetildiği adapter
 */
public class DetailItemListAdapter extends RecyclerView.Adapter<DetailItemListAdapter.DetailItemListViewHolder> {

   private final List<ItemListDetail> itemListDetails;
   private final NewsDetailNavigator navigator;

   public DetailItemListAdapter(List<ItemListDetail> itemListDetails, NewsDetailNavigator navigator) {
      this.itemListDetails = itemListDetails;
      this.navigator = navigator;
   }

   @NonNull
   @Override
   public DetailItemListAdapter.DetailItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      RecInNewsDetailItemListBinding binding = RecInNewsDetailItemListBinding.inflate(layoutInflater, parent, false);
      return new DetailItemListAdapter.DetailItemListViewHolder(binding, navigator);
   }

   @Override
   public void onBindViewHolder(@NonNull DetailItemListAdapter.DetailItemListViewHolder holder, int position) {
      ItemListDetail item = itemListDetails.get(position);
      holder.bind(item);
   }

   @Override
   public int getItemCount() {
      return itemListDetails.size();
   }

   static class DetailItemListViewHolder extends RecyclerView.ViewHolder {
      // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
      // Since our layout file is rec_in_main_news.xml, our auto generated binding class is RecInNewsBinding
      RecInNewsDetailItemListBinding binding;
      NewsDetailNavigator navigator;

      public DetailItemListViewHolder(RecInNewsDetailItemListBinding binding, NewsDetailNavigator navigator) {
         super(binding.getRoot());
         this.binding = binding;
         this.navigator = navigator;
      }

      public void bind(ItemListDetail itemList) {
         binding.setItemList(itemList);
         binding.executePendingBindings();
         binding.setNewsDetailNavigator(navigator);
      }
   }
}