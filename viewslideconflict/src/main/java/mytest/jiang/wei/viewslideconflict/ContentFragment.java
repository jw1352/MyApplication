package mytest.jiang.wei.viewslideconflict;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei.jiang on 2015/12/1.
 */
public class ContentFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 100; i ++) {
            list.add("text" + i);
        }
        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list));

        return view;
    }
}
