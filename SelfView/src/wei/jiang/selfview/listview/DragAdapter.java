package wei.jiang.selfview.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wei.jiang.selfview.R;

/**
 * Created by jiangwei on 2015/12/22.
 */
public abstract class DragAdapter extends BaseAdapter {

    private List<String> data;

    public DragAdapter(List<String> data) {
        this.data = data;
    }

    /**
     * 交换位置
     *
     * @param start
     * @param end
     */
    public void change(int start, int end) {
        String srcData = data.get(start);
        data.remove(srcData);
        data.add(end, srcData);
        notifyDataSetChanged();
    }
}
