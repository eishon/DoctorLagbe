package com.death_slash.eishon.daclient.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.death_slash.eishon.daclient.R;
import com.death_slash.eishon.daclient.domain.Appointment;

import java.util.List;

public class PAppointmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Appointment> appointments;

    public PAppointmentListAdapter(List<Appointment> a){
        appointments = a;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_p_appointment_list, viewGroup, false);
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
        return appointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View itemView;
        private int id;

        private final TextView doctorNameTextView;
        private final TextView appointmentStateTextView;
        private final TextView appointmentMsgTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            doctorNameTextView = itemView.findViewById(R.id.doctor_name_appointment_list_textview);
            appointmentStateTextView = itemView.findViewById(R.id.p_state_appointment_list_textview);
            appointmentMsgTextView = itemView.findViewById(R.id.p_msg_appointment_list_textview);
        }

        public void bindView(int pos) {
            String name = appointments.get(pos).getDoctor_name();
            String state = appointments.get(pos).getState();
            String msg = appointments.get(pos).getMessage();

            doctorNameTextView.setText(name);
            appointmentStateTextView.setText(state);
            appointmentMsgTextView.setText(msg);
        }
    }
}
