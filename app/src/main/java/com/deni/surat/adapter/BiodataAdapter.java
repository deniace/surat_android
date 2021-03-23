package com.deni.surat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deni.surat.R;
import com.deni.surat.databinding.BiodataWargaItemLayoutBinding;
import com.deni.surat.model.BiodataSingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deni Supriyatna (deni ace) on 09 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class BiodataAdapter extends RecyclerView.Adapter<BiodataAdapter.BiodataViewHolder>{
    private List<BiodataSingle> list;
    private List<BiodataSingle> listFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(BiodataSingle item);
    }

    public class BiodataViewHolder extends RecyclerView.ViewHolder{
        private BiodataWargaItemLayoutBinding binding;

        public BiodataViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public BiodataViewHolder(@NonNull BiodataWargaItemLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final BiodataSingle item, final OnItemClickListener listener){
            binding.setProfile(item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onItemClick(item);
                    }
                }
            });
        }
    }

    public BiodataAdapter(){
        list = new ArrayList<>();
        listFull = new ArrayList<>();
    }

    public BiodataAdapter(@NonNull List<BiodataSingle> list){
        this.list = list;
        listFull = new ArrayList<>(list);
    }

    public BiodataAdapter(@NonNull OnItemClickListener listener){
        list = new ArrayList<>();
        listFull = new ArrayList<>(list);
        this.listener = listener;
    }

    public BiodataAdapter(List<BiodataSingle> list, OnItemClickListener listener){
        this.list = list;
        listFull = new ArrayList<>(list);
        this.listener = listener;
    }

    public List<BiodataSingle> getList(){
        return list;
    }

    public List<BiodataSingle> getListFull(){
        return listFull;
    }

    public void addItems(List<BiodataSingle> items){
        list.addAll(items);
        listFull.addAll(items);
    }

    @NonNull
    @Override
    public BiodataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BiodataWargaItemLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.biodata_warga_item_layout, parent, false);
        BiodataViewHolder holder = new BiodataViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BiodataViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
