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

public class AppointmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Appointment> appointments;

    public AcceptAppoinmentListener acceptAppoinmentListener;
    public RejecttAppoinmentListener rejectAppoinmentListener;

    public AppointmentListAdapter(List<Appointment> a){
        appointments = a;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_appointment_list, viewGroup, false);
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

        private final TextView patientNameTextView;
        private final TextView appointmentStateTextView;
        private final TextView appointmentMsgTextView;
        private final Button acceptAppointmentBtn;
        private final Button rejectAppointmentBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            patientNameTextView = itemView.findViewById(R.id.name_appointment_list_textview);
            appointmentStateTextView = itemView.findViewById(R.id.state_appointment_list_textview);
            appointmentMsgTextView = itemView.findViewById(R.id.msg_appointment_list_textview);
            acceptAppointmentBtn = itemView.findViewById(R.id.accept_appointment_list_btn);
            rejectAppointmentBtn = itemView.findViewById(R.id.reject_appointment_list_btn);
        }

        public void bindView(int pos) {
            final String appointmentId = appointments.get(pos).getId();
            String name = appointments.get(pos).getPatient_name();
            String state = appointments.get(pos).getState();
            String msg = appointments.get(pos).getMessage();

            patientNameTextView.setText(name);
            appointmentStateTextView.setText(state);
            appointmentMsgTextView.setText(msg);

            acceptAppointmentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptAppoinmentListener.acceptAppointment(appointmentId);
                }
            });

            rejectAppointmentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rejectAppoinmentListener.rejectAppointment(appointmentId);
                }
            });
        }
    }
}
