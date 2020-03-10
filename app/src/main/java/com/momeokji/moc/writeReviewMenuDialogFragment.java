package com.momeokji.moc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.momeokji.moc.Adapters.RecyclerViewAdapter_WriteReviewExpandableMenuList;

import java.util.ArrayList;
import java.util.List;


public class writeReviewMenuDialogFragment extends DialogFragment {
/*
    private OnFragmentInteractionListener mListener;

    public writeReviewMenuDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_review_menu_dialog, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Context context = requireActivity();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog, null);
//       LayoutInflater inflater = LayoutInflater.from(getContext());
//        View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog, (ViewGroup) getActivity().findViewById(android.R.id.content),false);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
 //       LayoutInflater inflater = getActivity().getLayoutInflater();
 //       View view = inflater.inflate(R.layout.fragment_write_review_menu_dialog,null);
        builder.setView(view);
        builder.setTitle("메뉴 선택");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        RecyclerView menulist = (RecyclerView) view.findViewById(R.id.menulist);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        menulist.setLayoutManager(linearLayoutManager);


 //       menulist.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<RecyclerViewAdapter_WriteReviewExpandableMenuList.Item> data = new ArrayList<>();
        RecyclerViewAdapter_WriteReviewExpandableMenuList adapter = new RecyclerViewAdapter_WriteReviewExpandableMenuList(data);

        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.HEADER, "Fruits"));
        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, "Apple"));
        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, "Orange"));
        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, "Banana"));
        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.HEADER, "car"));
        data.add(new RecyclerViewAdapter_WriteReviewExpandableMenuList.Item(RecyclerViewAdapter_WriteReviewExpandableMenuList.CHILD, "ben"));
        menulist.setAdapter(new RecyclerViewAdapter_WriteReviewExpandableMenuList(data));

        return builder.create();
    }
}
