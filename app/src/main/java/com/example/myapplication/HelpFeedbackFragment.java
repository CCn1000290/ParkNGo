package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
public class HelpFeedbackFragment extends Fragment {
    private EditText feedbackInput;
    private Button submitButton;
    public HelpFeedbackFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_feedback, container, false);

        feedbackInput = view.findViewById(R.id.feedback_input);
        submitButton = view.findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback(feedbackInput.getText().toString());
            }
        });

        return view;
    }

    private void submitFeedback(String feedback) {
        // Here, you would implement the logic to handle the feedback submission.
        // This could involve sending the feedback to a server or saving it locally.

        // For demonstration, we're just printing the feedback to logs.
        System.out.println("Feedback submitted: " + feedback);
    }
}
