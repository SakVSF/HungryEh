package com.example.hungryeh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.coordinatorlayout.widget.DirectedAcyclicGraph;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeslotDialog extends AppCompatDialogFragment {

    private TextView tv_textview_timeslot;
    private NumberPicker np_timeslotPicker;
    private Button btn_button_confirmTimeslot;
    ArrayList<String> available_timeslots;
    public String str_TimeslotSelection = "";

    public TimeslotDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_timeslot_dialog, null);
        builder.setView(view);

        //assume load timeslots
        available_timeslots = new ArrayList<>();
        available_timeslots.add("12:00-12:30");
        available_timeslots.add("12:30-13:00");
        available_timeslots.add("13:00-13:30");
        available_timeslots.add("13:30-14:00");
        available_timeslots.add("14:00-14:30");

        tv_textview_timeslot = view.findViewById(R.id.textview_timeslot);
        np_timeslotPicker = view.findViewById(R.id.timeslotPicker);
        np_timeslotPicker.setMinValue(0);
        np_timeslotPicker.setMaxValue(available_timeslots.size()-1);
        String[] arr_available_timeslots = (String[]) available_timeslots.toArray(new String[0]);
        str_TimeslotSelection = arr_available_timeslots[0];
        np_timeslotPicker.setDisplayedValues(arr_available_timeslots);
        np_timeslotPicker.setWrapSelectorWheel(false);
        btn_button_confirmTimeslot = view.findViewById(R.id.button_confirmTimeslot);

        np_timeslotPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                str_TimeslotSelection = arr_available_timeslots[newVal];

            }
        });

        btn_button_confirmTimeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // str_TimeslotSelection
                Intent intent = new Intent(view.getContext() , stall_item_page_activity.class);
                intent.putExtra("str_TimeslotSelection",str_TimeslotSelection);
                //stall_item_page_activity
                startActivity(intent);
                dismiss();
            }
        });

        return builder.create();


    }


}