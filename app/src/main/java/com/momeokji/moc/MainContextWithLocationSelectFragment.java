package com.momeokji.moc;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.momeokji.moc.MainActivity.displayedFragmentManager;

public class MainContextWithLocationSelectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_context_with_location_select, container, false);
        // AddLocationSelectSpinner();
        return view;
    }

    public void AddLocationSelectSpinner() {
/*        //* 지역 선택용 스피너 등록
        Spinner locationSelect_spinner;
        TextView location_txt;
        locationSelect_spinner = view.findViewById(R.id.locationSelect_spinner);
        location_txt = view.findViewById(R.id.location_txt);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, ((MainActivity) getActivity()).restaurantDATA.Location); // TODO 서버 데이터 : Fragment 생성될 때 마다 Location 리스트
        locationSelect_spinner.setSelection(0);
        location_txt.setText((String) locationSelect_spinner.getItemAtPosition(0));
        locationSelect_spinner.setAdapter(arrayAdapter);
        locationSelect_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedItem = (String) adapterView.getItemAtPosition(position);
                location_txt.setText(selectedItem);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
    }
}
