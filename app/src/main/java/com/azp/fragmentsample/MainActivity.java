package com.azp.fragmentsample;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
    implements SimpleFragment.OnFragmentInteractionListener{

    private Button mButton;
    private boolean isFragmentDisplayed = false;

    static final String STATE_FRAGMENT = "state_of_fragment";
    static final String STATE_CHOICE = "user_choice";

    private int mRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.open_button);

        //If returning from a configuration change, get the fragment state and set the button text
        if (savedInstanceState != null){
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            mRadioButtonChoice = savedInstanceState.getInt(STATE_CHOICE);
            if (isFragmentDisplayed) {
                mButton.setText(R.string.close);
            }
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
    }

    public void displayFragment() {

        //Instantiate the fragment.
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);

        //Get the FragmentManager and start a transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the SimpleFragment
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();

        //Update the Button Text
        mButton.setText(R.string.close);

        isFragmentDisplayed = true;
    }

    public void closeFragment() {

        //Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();

        //Check to see if the fragment is already showing.
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);

        if (simpleFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }

        mButton.setText(R.string.open);

        isFragmentDisplayed = false;
    }


    /**
     * This method keeps in memory the radio button choice the user selected
     * and displays a Toast to show it.
     * @param choice
     */
    @Override
    public void onRadioButtonChoice(int choice) {
        //keep the radio button choice to pass it back to the fragment
        mRadioButtonChoice = choice;
        //Show a Toast with the radio button choice.
        Toast.makeText(this, "Choice is " + Integer.toString(choice),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        savedInstanceState.putInt(STATE_CHOICE, mRadioButtonChoice);
        super.onSaveInstanceState(savedInstanceState);
    }
}
