package mytest.jiang.wei.viewslideconflict;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by wei.jiang on 2015/12/1.
 */
public abstract class MenuActivity extends FragmentActivity {
    private FragmentManager fm;
    private Fragment leftFragment;
    private Fragment rightFragment;
    private Fragment contentFragment;
    private MySlideMenu mySlideMenu;
    private boolean hasLeftMenu = false;
    private boolean hasRightMenu = false;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        mySlideMenu = (MySlideMenu) findViewById(R.id.mySlideMenu);
        // 子类设置菜单的回调
        initFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (leftFragment != null)
            ft.replace(R.id.leftFrame, leftFragment);
        if (contentFragment != null)
            ft.replace(R.id.contentFrame, contentFragment);
        if (rightFragment != null)
            ft.replace(R.id.rightFrame, rightFragment);
        ft.commit();
    }
    public void setLeftFragment(Fragment leftFragment) {
        this.leftFragment = leftFragment;
        hasLeftMenu = true;
    }
    public void setContentFragment(Fragment contentFragment) {
        this.contentFragment = contentFragment;
    };
    public void setRightFragment(Fragment rightFragment) {
        this.rightFragment = rightFragment;
        hasRightMenu = true;
    }
    public void openLeft() {
        if (hasLeftMenu)
            mySlideMenu.openLeft();
    }
    public void openRight() {
        if (hasRightMenu)
            mySlideMenu.openRight();
    }
    public void resume() {
        mySlideMenu.resume();
    }
    public abstract void initFragment();
}

