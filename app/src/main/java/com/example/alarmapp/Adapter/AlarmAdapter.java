package com.example.alarmapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Interface.IClickListener;
import com.example.alarmapp.Model.Alarm;
import com.example.alarmapp.R;

import java.util.List;
import java.util.Locale;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private Context context;
    private List<Alarm> alarmList;
    private int rowLayout;
    private IClickListener itemClickListener;
    public AlarmAdapter(List<Alarm> alarmList, int rowLayout, Context context) {
        this.context = context;
        this.alarmList = alarmList;
        this.rowLayout = rowLayout;
    }

    public void setData(List<Alarm> list){
        this.alarmList = list;
        notifyDataSetChanged();
    }
//    public void add(Alarm alarm){
//        alarmList.add(alarm);
//        notifyDataSetChanged();
//    }
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        if(alarm == null){
            return;
        }
        String sHour = String.format(Locale.getDefault(), "%02d", alarm.getHour());
        String sMinute = String.format(Locale.getDefault(), "%02d", alarm.getMinute());
        String time = sHour + ":" + sMinute;

        holder.imgAlarm.setImageResource(alarm.getAlarm_ImgID());
        holder.txtAlarmTime.setText(time);
        holder.txtAlarmNote.setText(alarm.getNote());
        holder.btnDayRepeat.setEnabled(true);
        holder.btn_Mon.setChecked(alarm.isMonday());
        holder.btn_Tue.setChecked(alarm.isTuesday());
        holder.btn_Wed.setChecked(alarm.isWednesday());
        holder.btn_Thu.setChecked(alarm.isThursday());
        holder.btn_Fri.setChecked(alarm.isFriday());
        holder.btn_Sat.setChecked(alarm.isSaturday());
        holder.btn_Sun.setChecked(alarm.isSunday());

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                alarmList.remove(holder.getAdapterPosition());
//
//                notifyItemRemoved(holder.getAdapterPosition());
//                notifyItemRangeChanged(holder.getAdapterPosition(), alarmList.size());
                itemClickListener.deleteAlarm(alarm);
                alarm.cancelAlarm(view.getContext());
            }
        });
        holder.onOffAlarm.setChecked(alarm.isStarted());
        holder.onOffAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    alarm.schedule(context.getApplicationContext());
                } else {
                    alarm.cancelAlarm(context.getApplicationContext());
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        if(alarmList != null) {
            return alarmList.size();
        }
        return 0;
    }

    public void setClickListener(IClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgAlarm;
        private TextView txtAlarmTime;
        private TextView txtAlarmNote;
        private Button btnDayRepeat;
        private SwitchCompat onOffAlarm;
        private Button btn_delete;
        private CardView itemClick;
        private ToggleButton btn_Mon, btn_Tue, btn_Wed, btn_Thu, btn_Fri, btn_Sat, btn_Sun;

        public AlarmViewHolder(@NonNull View v) {
            super(v);
            imgAlarm = v.findViewById(R.id.img_setAlarm);
            txtAlarmTime = v.findViewById(R.id.txt_TimeAlarm_Fragment);
            txtAlarmNote = v.findViewById(R.id.txt_alarmNote_Fragment);
            btnDayRepeat = v.findViewById(R.id.btn_Mon_Fragment);
            itemClick = v.findViewById(R.id.item_click);
            btn_Mon = v.findViewById(R.id.btn_Mon_Fragment);
            btn_Tue = v.findViewById(R.id.btn_Tue_Fragment);
            btn_Wed = v.findViewById(R.id.btn_Wed_Fragment);
            btn_Thu = v.findViewById(R.id.btn_Thu_Fragment);
            btn_Fri = v.findViewById(R.id.btn_Fri_Fragment);
            btn_Sat = v.findViewById(R.id.btn_Sat_Fragment);
            btn_Sun = v.findViewById(R.id.btn_Sun_Fragment);
            btn_delete = v.findViewById(R.id.btn_DeleteAlarm);
            onOffAlarm= v.findViewById(R.id.switchBtn_Fragment);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener != null){
                itemClickListener.onClickItem(view, getAdapterPosition());
            }
        }

    }
}
