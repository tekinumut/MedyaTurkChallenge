package com.example.medyaturkchallenge.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medyaturkchallenge.data.remote.models.ItemList;
import com.example.medyaturkchallenge.databinding.PagerInSwipeBinding;
import com.example.medyaturkchallenge.ui.main.MainNavigator;
import com.example.medyaturkchallenge.utils.Utils;

import java.util.List;

public class SwipePagerAdapter extends RecyclerView.Adapter<SwipePagerAdapter.SliderViewHolder> {

   private final List<ItemList> itemLists;
   private final MainNavigator mainNavigator;

   public SwipePagerAdapter(List<ItemList> itemLists, MainNavigator mainNavigator) {
      this.itemLists = itemLists;
      this.mainNavigator = mainNavigator;
   }

   @NonNull
   @Override
   public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      PagerInSwipeBinding binding = PagerInSwipeBinding.inflate(layoutInflater, parent, false);
      return new SliderViewHolder(binding, mainNavigator);
   }

   @Override
   public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
      // Sadece ilk iteme özel konum değişiklikleri
      if (position == 0) {
         ViewGroup.MarginLayoutParams paramsCardView = new ViewGroup.MarginLayoutParams(holder.binding.nonCardView.getLayoutParams());
         paramsCardView.setMargins(0, 0, 0, 0);
         holder.binding.nonCardView.setLayoutParams(paramsCardView);

         // Aldığı widget'ın sağına 30dp boşluk ekler
         Utils.addMarginStartEnd(holder.binding.imageSwipe, 30, 1);
         Utils.addMarginStartEnd(holder.binding.publishDateSwipe, 30, 1);
      }

      ItemList item = itemLists.get(position);
      holder.bind(item);
   }

   @Override
   public int getItemCount() {
      return itemLists.size();
   }

   static class SliderViewHolder extends RecyclerView.ViewHolder {
      // If your layout file is something_awesome.xml then your binding class will be SomethingAwesomeBinding
      // Since our layout file is pager_in_swipe.xml, our auto generated binding class is PagerInSwipeBinding
      PagerInSwipeBinding binding;
      MainNavigator mainNavigator;

      public SliderViewHolder(PagerInSwipeBinding binding, MainNavigator mainNavigator) {
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