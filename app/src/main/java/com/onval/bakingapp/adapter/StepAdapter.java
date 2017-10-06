package com.onval.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.view.IStepDetailView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gval on 30/09/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {
    private Context context;
    private ArrayList<Step> stepList;
    private IStepDetailView.Listener listener;


    public StepAdapter(Context context, List<Step> stepList,
                       IStepDetailView.Listener listener) {
        this.context = context;
        this.stepList = (ArrayList<Step>) stepList;
        this.listener = listener;
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_step, parent, false);

        final StepHolder holder = new StepHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                listener.onStepClicked(stepList.get(position).getId());
            }
        });

        return holder;
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        holder.bind(position);
    }

    public Step findStepById(int id) {
        Step step;

        for (int i = 0; i <= stepList.size(); i++) {
            step = stepList.get(i);

            if (step.getId() == id)
                return step;
        }

        //This should never be called
        return null;
    }

    class StepHolder extends RecyclerView.ViewHolder {
        TextView stepDescription;

        StepHolder(View view) {
            super(view);
            stepDescription = (TextView) view.findViewById(R.id.single_step_tv);
        }

        void bind(int position) {
            stepDescription.setText(position + ". " + stepList.get(position).getShortDescription());
        }
    }
}
