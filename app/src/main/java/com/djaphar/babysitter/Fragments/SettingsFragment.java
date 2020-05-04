package com.djaphar.babysitter.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.djaphar.babysitter.Activities.MainActivity;
import com.djaphar.babysitter.R;
import com.djaphar.babysitter.SupportClasses.Adapters.MealRecyclerViewAdapter;
import com.djaphar.babysitter.SupportClasses.ApiClasses.Meal;
import com.djaphar.babysitter.SupportClasses.OtherClasses.MyFragment;
import com.djaphar.babysitter.SupportClasses.OtherClasses.ViewDriver;
import com.djaphar.babysitter.ViewModels.SettingsViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsFragment extends MyFragment {

    private SettingsViewModel settingsViewModel;
    private MainActivity mainActivity;
    private Context context;
    private RecyclerView mealRecyclerView;
    private ConstraintLayout settingsContainer, mealsContainer;
    private TextView logoutTv, mealListTv;
    private ImageButton newMealBtn;
    private ArrayList<Meal> meals;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings,  container, false);
        mealRecyclerView = root.findViewById(R.id.meal_recycler_view);
        settingsContainer = root.findViewById(R.id.settings_container);
        mealsContainer = root.findViewById(R.id.meals_container);
        logoutTv = root.findViewById(R.id.logout_tv);
        mealListTv = root.findViewById(R.id.meal_list_tv);
        newMealBtn = root.findViewById(R.id.new_meal_btn);
        context = getContext();
        mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            setActionBarTitle(getString(R.string.settings));
            setBackBtnState(false);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                return;
            }
            mainActivity.logout();
        });

        settingsViewModel.getMeals().observe(getViewLifecycleOwner(), meals -> {
            if (meals == null) {
                return;
            }
            this.meals = meals;
        });

        logoutTv.setOnClickListener(lView -> showStandardDialog(getString(R.string.logout_text), getString(R.string.logout_message), null));

        mealListTv.setOnClickListener(lView -> showMealsContainer());

        newMealBtn.setOnClickListener(lView -> {
            View inflatedView = View.inflate(context, R.layout.main_dialog, null);
            EditText fieldChangeEd = inflatedView.findViewById(R.id.field_change_ed);
            fieldChangeEd.setHint(R.string.new_meal_text);
            new AlertDialog.Builder(context)
                    .setView(inflatedView)
                    .setTitle(R.string.new_meal_title)
                    .setNegativeButton(R.string.cancel_button, (dialogInterface, i) -> dialogInterface.cancel())
                    .setPositiveButton(R.string.ok_button, (dialogInterface, i) -> {

                    })
                    .show();
        });
    }

    private void setActionBarTitle(String title) {
        mainActivity.setActionBarTitle(title);
    }

    private void setBackBtnState(boolean visible) {
        mainActivity.setBackBtnState(visible);
    }

    public boolean everythingIsClosed() {
        return mealsContainer.getVisibility() != View.VISIBLE;
    }

    public void backWasPressed() {
        settingsContainer.setVisibility(View.VISIBLE);
        setActionBarTitle(getString(R.string.settings));
        setBackBtnState(false);
        ViewDriver.toggleChildViewsEnable(mealsContainer, false);
        ViewDriver.hideView(mealsContainer, R.anim.hide_right_animation, context);
    }

    public void deleteMeal(Meal meal) {
        showStandardDialog(getString(R.string.delete_meal_title), getString(R.string.delete_meal_message), meal);
    }

    private void showMealsContainer() {
        mealRecyclerView.setAdapter(new MealRecyclerViewAdapter(meals, this));
        mealRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        setActionBarTitle(getString(R.string.meal_list_tv_text));
        setBackBtnState(true);
        ViewDriver.toggleChildViewsEnable(mealsContainer, true);
        ViewDriver.showView(mealsContainer, R.anim.show_right_animation, context).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                settingsContainer.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }

    private void showStandardDialog(String title, String message, Meal meal) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(R.string.cancel_button, (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton(R.string.ok_button, (dialogInterface, i) -> {
                    if (meal == null) {
                        settingsViewModel.logout();
                    } else {

                    }
                })
                .show();
    }
}