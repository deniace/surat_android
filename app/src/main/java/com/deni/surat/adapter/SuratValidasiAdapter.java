package com.deni.surat.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.deni.surat.R;
import com.deni.surat.databinding.ValidasiItemLayoutBinding;
import com.deni.surat.model.SuratSingle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deni Supriyatna (deni ace) on 09 - 06 - 2020.
 * Email : denisupriyatna01@gmail.com
 */
public class SuratValidasiAdapter extends RecyclerView.Adapter<SuratValidasiAdapter.SuratValidasiViewHolder>{
    private List<SuratSingle> list;
    private List<SuratSingle> listFull;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        public void onItemClick(SuratSingle item);
    }

    public class SuratValidasiViewHolder extends RecyclerView.ViewHolder{
        private ValidasiItemLayoutBinding binding;

        public SuratValidasiViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public SuratValidasiViewHolder(@NonNull ValidasiItemLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final SuratSingle item, final OnItemClickListener listener){
            binding.setSurat(item);
            switch (item.getIdStatusRt()){
                case "0" :
                    binding.tvStatusRtSuratItem.setText("Menunggu Persetujuan RT");
                    binding.tvStatusRtSuratItem.setTextColor(Color.BLACK);
                    break;
                case "1" :
                    binding.tvStatusRtSuratItem.setText("Sudah disetujui RT");
                    binding.tvStatusRtSuratItem.setTextColor(Color.GREEN);
                    break;
                case "2" :
                    binding.tvStatusRtSuratItem.setText("Persetujuan ditolak RT");
                    binding.tvStatusRtSuratItem.setTextColor(Color.RED);
                    break;
            }
            switch (item.getIdStatusRw()){
                case "0" :
                    binding.tvStatusRwSuratItem.setText("Menunggu Persetujuan RW");
                    binding.tvStatusRwSuratItem.setTextColor(Color.BLACK);
                    break;
                case "1" :
                    binding.tvStatusRwSuratItem.setText("Sudah disetujui RW");
                    binding.tvStatusRwSuratItem.setTextColor(Color.GREEN);
                    break;
                case "2" :
                    binding.tvStatusRwSuratItem.setText("Persetujuan ditolak RW");
                    binding.tvStatusRwSuratItem.setTextColor(Color.RED);
                    break;
            }
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

    public SuratValidasiAdapter(){
        list = new ArrayList<>();
        listFull = new ArrayList<>();
    }

    public SuratValidasiAdapter(@NonNull List<SuratSingle> list){
        this.list = list;
        listFull = new ArrayList<>(list);
    }

    public SuratValidasiAdapter(@NonNull OnItemClickListener listener){
        list = new ArrayList<>();
        listFull = new ArrayList<>(list);
        this.listener = listener;
    }

    public SuratValidasiAdapter(List<SuratSingle> list, OnItemClickListener listener){
        this.list = list;
        listFull = new ArrayList<>(list);
        this.listener = listener;
    }

    public List<SuratSingle> getList(){
        return list;
    }

    public List<SuratSingle> getListFull(){
        return listFull;
    }

    public void addItems(List<SuratSingle> items){
        list.addAll(items);
        listFull.addAll(items);
    }

    @NonNull
    @Override
    public SuratValidasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ValidasiItemLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.validasi_item_layout, parent, false);
        SuratValidasiViewHolder holder = new SuratValidasiViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SuratValidasiViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
