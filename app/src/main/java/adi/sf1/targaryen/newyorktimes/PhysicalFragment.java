package adi.sf1.targaryen.newyorktimes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PhysicalFragment extends Fragment {
  public PhysicalFragment() {

  }

  @Override

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_physical, container, false);

    return view;

  }

}