package wei.jiang.selfview.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import wei.jiang.selfview.R;

public class DragListViewActivity extends Activity {

    private ListView dragListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_list_view);

        dragListView = (ListView) findViewById(R.id.dragListView);
        List<String> data = initData();
        DragAdapter dragAdapter = new DragAdapter(data,this);
        dragListView.setAdapter(dragAdapter);
    }

    private List<String> initData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            data.add("标题xxx" + i);
        }
        return data;
    }
}
