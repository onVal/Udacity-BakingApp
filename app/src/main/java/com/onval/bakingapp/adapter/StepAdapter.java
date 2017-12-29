package com.onval.bakingapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.data.Step;
import com.onval.bakingapp.view.StepDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gval on 30/09/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {
    private Context context;
    private ArrayList<Step> stepList;
    private StepDetailFragment.OnStepClickListener listener;

    private int selectedItem;

    public StepAdapter(Context context, List<Step> stepList,
                       StepDetailFragment.OnStepClickListener listener) {
        this.context = context;
        this.stepList = (ArrayList<Step>) stepList;
        this.listener = listener;

        selectedItem = 0; //when creating adapter for the first time select the first item
    }

    //alternative constructor to allow to mantain selection state across config changes
    public StepAdapter(Context context, List<Step> stepList,
                       StepDetailFragment.OnStepClickListener listener, int selectedItem) {
        this(context, stepList, listener);
        this.selectedItem = selectedItem;
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

                //after clicking on an item, update both the old and the new ones
                notifyItemChanged(selectedItem);
                selectedItem = position;
                notifyItemChanged(selectedItem);
            }
        });

        return holder;
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    //todo: retain state selection upon rotation (probably not only from here)
    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        if (context.getResources().getBoolean(R.bool.isTablet)) {
            if (position == selectedItem) {
                holder.stepDescription.setTextColor(Color.WHITE);
                holder.stepDescription.setBackgroundColor(
                        context.getResources().getColor(R.color.selectedListItemBackground));
            } else {
                holder.stepDescription.setTextColor(Color.BLACK);
                holder.stepDescription.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        holder.bind(position);
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    //returns -1 if it doesn't find it
    public static int findStepPositionById(List<Step> stepList, int id) {


        for (int i = 0; i <= stepList.size(); i++) {
            if (stepList.get(i).getId() == id)
                return i;
        }
        return -1;
    }

    public ArrayList<Step> getStepList() {
        return stepList;
    }

    //View holder //////////////////////////////////////////////////////////////////////////////

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
