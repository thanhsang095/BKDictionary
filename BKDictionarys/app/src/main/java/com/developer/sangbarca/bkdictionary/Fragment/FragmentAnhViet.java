package com.developer.sangbarca.bkdictionary.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.sangbarca.bkdictionary.R;

/**
 * Created by SANG BARCA on 5/6/2017.
 */

public class FragmentAnhViet extends Fragment {
    TextView txtWordName, txtWordPronounce, txtWordCategory,
            txtWordCategoryName, txtWordTuLoai, txtWordMean, txtWordExample, txtWordExampleContent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anhviet, container, false);
        AnhXa(view);
        //Get intent from Activity
        Bundle myBundle = getArguments();
        if(myBundle != null) {
            txtWordName.setText(myBundle.getString("getName"));
            txtWordPronounce.setText(myBundle.getString("getPronouce"));
            txtWordCategoryName.setText(myBundle.getString("getCategory"));
            txtWordTuLoai.setText(myBundle.getString("getTuLoai"));
            txtWordMean.setText(myBundle.getString("getMean"));
            txtWordExampleContent.setText(myBundle.getString("getExample"));
        }else {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
        }



        return view;
    }

    private void AnhXa(View view){
        txtWordName = (TextView) view.findViewById(R.id.textViewWordName);
        txtWordPronounce = (TextView) view.findViewById(R.id.textViewWordPronounce);
        txtWordCategory = (TextView) view.findViewById(R.id.textViewWordCategory);
        txtWordCategoryName = (TextView) view.findViewById(R.id.textViewWordcategoryName);
        txtWordTuLoai = (TextView) view.findViewById(R.id.textViewWordTuLoai);
        txtWordMean = (TextView) view.findViewById(R.id.textViewWordMean);
        txtWordExample = (TextView) view.findViewById(R.id.textViewWordExample);
        txtWordExampleContent = (TextView) view.findViewById(R.id.textViewWordExampleContent);
    }
}
