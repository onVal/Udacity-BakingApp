package com.onval.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gval on 30/09/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {
    Context context;
    ArrayList<Step> stepList;
    View.OnClickListener listener;


    public StepAdapter(Context context, List<Step> stepList,
                       View.OnClickListener listener) {
        this.context = context;
        this.stepList = (ArrayList<Step>) stepList;
        this.listener = listener;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = (TextView) LayoutInflater.from(context)
                .inflate(R.layout.single_step, parent, false);

        return new StepHolder(view);

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        holder.bind(position);

    }

    public class StepHolder extends RecyclerView.ViewHolder {
        TextView stepDescription;

        public StepHolder(View view) {
            super(view);
            view.setOnClickListener(listener);
            stepDescription = (TextView) view; //todo: is this cool?
        }

        public void bind(int position) {
            stepDescription.setText(stepList.get(position).getShortDescription());
        }

    }
}
