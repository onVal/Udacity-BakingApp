package com.onval.bakingapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.provider.RecipeContract.StepsEntry;
import com.onval.bakingapp.view.StepDetailFragment;

/**
 * Created by gval on 30/09/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {
    private Context context;
    private Cursor stepCursor;
    private StepDetailFragment.OnStepClickListener listener;

    private int selectedItem;

    public StepAdapter(Context context, Cursor stepCursor,
                       StepDetailFragment.OnStepClickListener listener) {
        this.context = context;
        this.stepCursor = stepCursor;
        this.listener = listener;

        selectedItem = 0; //when creating adapter for the first time select the first item
    }

    //alternative constructor to allow to mantain selection state across config changes
    public StepAdapter(Context context, Cursor stepCursor,
                       StepDetailFragment.OnStepClickListener listener, int selectedItem) {
        this(context, stepCursor, listener);
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
                listener.onStepClicked(position);

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
        return stepCursor.getCount();
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

    //View holder //////////////////////////////////////////////////////////////////////////////
    class StepHolder extends RecyclerView.ViewHolder {
        TextView stepDescription;

        StepHolder(View view) {
            super(view);
            stepDescription = (TextView) view.findViewById(R.id.single_step_tv);
        }

        void bind(int position) {
            stepCursor.moveToPosition(position);
            //column 0 is the step description (projection is applied in rawquery inside provider)
            stepDescription.setText(position + ". " + stepCursor.getString(StepsEntry.SHORT_DESC));
        }
    }
}
