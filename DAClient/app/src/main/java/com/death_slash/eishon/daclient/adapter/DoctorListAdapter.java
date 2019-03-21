package com.death_slash.eishon.daclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.death_slash.eishon.daclient.R;
import com.death_slash.eishon.daclient.domain.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Doctor> doctors;

    public DoctorInfoListener doctorInfoListener;
    public SetAppointmentListener setAppointmentListener;

    public DoctorListAdapter(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doctor_list, parent, false);
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
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private int id;

        private final TextView doctorNameTextView;
        private final Button getDoctorInfoBtn;
        private final Button setAppointmentBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            doctorNameTextView = itemView.findViewById(R.id.name_doctor_list_textview);
            getDoctorInfoBtn = itemView.findViewById(R.id.info_doctor_list_btn);
            setAppointmentBtn = itemView.findViewById(R.id.appointment_doctor_list_btn);
        }

        public void bindView(int pos) {
            final String doctorId = doctors.get(pos).getMobile();
            String name = doctors.get(pos).getName();
            doctorNameTextView.setText(name);

            getDoctorInfoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doctorInfoListener.getDoctorInfo(doctorId);
                }
            });
            setAppointmentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAppointmentListener.setAppointment(doctorId);
                }
            });
        }
    }
}
