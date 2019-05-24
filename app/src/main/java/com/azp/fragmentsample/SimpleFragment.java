package com.azp.fragmentsample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SimpleFragment extends Fragment {

    //The radio button choice has 3 state: 0 = yes, 1 = no, 2 = default (no choice).
    // Using all 3 states.
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    //Initialize the choice to the default (no choice)
    private int mRadioButtonChoice = NONE;

    //The "choice" key for the bundle.
    private static final String CHOICE = "choice";

    //The listener interface
    OnFragmentInteractionListener mListener;

//    public SimpleFragment() {
//
//    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
        }
    }

    /**
     * Create the fragment view by inflating the layout, and if a choice has already been made,
     * get the choice argument from the bundle and check th appropriate choice in the fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        if (getArguments().containsKey(CHOICE)) {
            mRadioButtonChoice = getArguments().getInt(CHOICE);

            if (mRadioButtonChoice != NONE) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        //set the radioGroup onCheckedChanged listener
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        View radioButton = radioGroup.findViewById(checkedId);
                        int index = radioGroup.indexOfChild(radioButton);
                        TextView textView = rootView.findViewById(R.id.fragment_header);
                        switch (index) {
                            case YES:
                                textView.setText(R.string.yes_message);
                                mRadioButtonChoice = YES;
                                mListener.onRadioButtonChoice(YES);
                                break;

                            case NO:
                                textView.setText(R.string.no_message);
                                mRadioButtonChoice = NO;
                                mListener.onRadioButtonChoice(NO);
                                break;

                            default:
                                mRadioButtonChoice = NONE;
                                mListener.onRadioButtonChoice(NONE);
                                break;

                        }
                    }
                }
        );

        return rootView;
    }

    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }


}
