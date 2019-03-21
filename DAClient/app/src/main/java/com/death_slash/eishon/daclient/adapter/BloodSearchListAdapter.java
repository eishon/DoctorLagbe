package com.death_slash.eishon.daclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.death_slash.eishon.daclient.R;
import com.death_slash.eishon.daclient.domain.BloodSearch;

import java.util.List;

public class BloodSearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<BloodSearch> bloodSearches;

    public CallDonorListener callDonorListener;

    public BloodSearchListAdapter(List<BloodSearch> b){
        bloodSearches = b;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bood_search_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        try {
            ViewHolder vh = (ViewHolder) viewHolder;
            vh.bindView(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return bloodSearches.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private int id;

        private final TextView donorNameTextView;
        private final TextView donorDistrictTextView;
        private final Button callDonorBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            donorNameTextView = itemView.findViewById(R.id.name_bloodsearch_textview);
            donorDistrictTextView = itemView.findViewById(R.id.district_bloodsearch_textview);
            callDonorBtn = itemView.findViewById(R.id.call_bloodsearch_btn);
        }

        public void bindView(final int pos) {
            String name = bloodSearches.get(pos).getName();
            String district = bloodSearches.get(pos).getDistrict();

            donorNameTextView.setText(name);
            donorDistrictTextView.setText(district);

            callDonorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callDonorListener.makeCall(bloodSearches.get(pos).getMobile());
                }
            });
        }
    }
}
