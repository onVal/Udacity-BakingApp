package com.onval.bakingapp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onval.bakingapp.R;
import com.onval.bakingapp.Recipe;

import static com.onval.bakingapp.Recipe.RECIPE_PARCEL;


public class StepDetailFragment extends Fragment {
    TextView ingredients;

    public StepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);

        Bundle recipeBundle = getActivity().getIntent().getExtras();
        Recipe recipeParcel = recipeBundle.getParcelable(RECIPE_PARCEL);

        ingredients = (TextView) root.findViewById(R.id.steps_ingredients);
        ingredients.setText(recipeParcel.getName());

        return root;
    }

}
