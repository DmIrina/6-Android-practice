package com.example.lab3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SelectionFragment extends Fragment {

    private Button okButton;
    private Button cancelButton;
    private RadioGroup facultyGroup;
    private RadioGroup courseGroup;
    private RadioButton courseButton;
    private RadioButton facultyButton;

    public SelectionFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_selection, container, false);

        okButton = view.findViewById(R.id.okButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        facultyGroup = view.findViewById(R.id.facultyGroup);
        courseGroup = view.findViewById(R.id.courseGroup);

        okButton.setEnabled(false);


        facultyGroup.setOnCheckedChangeListener((group, checkedId) -> checkIfAllOptionsSelected());

        courseGroup.setOnCheckedChangeListener((group, checkedId) -> checkIfAllOptionsSelected());

        okButton.setOnClickListener(v -> {
            int facultyId = facultyGroup.getCheckedRadioButtonId();
            int courseId = courseGroup.getCheckedRadioButtonId();

            facultyButton = view.findViewById(facultyId);
            courseButton = view.findViewById(courseId);

            facultyGroup.setOnCheckedChangeListener((group, checkedId) -> checkIfAllOptionsSelected());
            courseGroup.setOnCheckedChangeListener((group, checkedId) -> checkIfAllOptionsSelected());

            String selectedFaculty = facultyButton.getText().toString();
            String selectedCourse = courseButton.getText().toString();

            if (writeResultToFile(selectedFaculty, selectedCourse)) {
                ResultFragment resultFragment = ResultFragment.newInstance(selectedFaculty, selectedCourse);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, resultFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(requireContext(), "Помилка запису до файлу",
                        Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> {
            facultyGroup.clearCheck();
            courseGroup.clearCheck();
        });

        return view;
    }

    private boolean writeResultToFile(String faculty, String course) {
        try {
            File file = new File(requireContext().getFilesDir(), "result.txt");
            FileWriter writer = new FileWriter(file, true);
            writer.append("Факультет: ").append(faculty).append(", курс: ").append(course).append("\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void checkIfAllOptionsSelected() {
        boolean isFacultySelected = facultyGroup.getCheckedRadioButtonId() != -1;
        boolean isCourseSelected = courseGroup.getCheckedRadioButtonId() != -1;

        okButton.setEnabled(isFacultySelected && isCourseSelected);
    }
}

