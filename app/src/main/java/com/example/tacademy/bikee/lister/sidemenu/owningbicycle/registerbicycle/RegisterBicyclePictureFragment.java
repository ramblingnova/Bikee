package com.example.tacademy.bikee.lister.sidemenu.owningbicycle.registerbicycle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.bikee.R;

import java.io.File;

public class RegisterBicyclePictureFragment extends Fragment {
    private File file1;
    private File file2;

    public static RegisterBicyclePictureFragment newInstance() {
        return new RegisterBicyclePictureFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_bicycle_picture, container, false);
    }

    public File getFile1() {
        return new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_2015-11-01-13-38-15.png");
    }

    public File getFile2() {
        return new File("/storage/emulated/0/Pictures/Screenshots/Screenshot_2015-11-01-13-38-17.png");
    }
}
