package mytest.jiang.wei.fragmentdemo;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5;
    private FragmentManager fm;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private Button button12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);
        button11 = (Button) findViewById(R.id.button11);
        button12 = (Button) findViewById(R.id.button12);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);

        fm = getSupportFragmentManager();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            addFragment1();
        } else if (v.getId() == R.id.button2) {
            addFragment2();
        } else if (v.getId() == R.id.button3) {
            removeFragment1();
        } else if (v.getId() == R.id.button4) {
            removeFragment2();
        } else if (v.getId() == R.id.button5) {
            replaceFragment2();
        } else if (v.getId() == R.id.button6) {
            popFragment();
        } else if (v.getId() == R.id.button7) {
            attachFragment();
        } else if (v.getId() == R.id.button8) {
            detachFragment();
        } else if (v.getId() == R.id.button9) {
            hideFragment1();
        } else if (v.getId() == R.id.button10) {
            hideFragment2();
        } else if (v.getId() == R.id.button11) {
            showFragment1();
        } else if (v.getId() == R.id.button12) {
            showFragment2();

        }

        if (fm.getFragments() != null) {
            Log.d("MainActivity", "fragment size :" + fm.getFragments().size());
            Log.d("MainActivity", "BackStackEntryCount :" + fm.getBackStackEntryCount());
        }
    }

    private void hideFragment2() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment2");
        ft.hide(fragment);
        ft.addToBackStack("fragment2");
        ft.commit();
    }

    private void hideFragment1() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment1");
        ft.hide(fragment);
        ft.addToBackStack("fragment1");
        ft.commit();
    }

    private void showFragment2() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment2");
        ft.show(fragment);
        ft.addToBackStack("fragment2");
        ft.commit();
    }

    private void showFragment1() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment1");
        ft.show(fragment);
        ft.addToBackStack("fragment1");
        ft.commit();

    }

    private void detachFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment2");
        ft.detach(fragment);
        ft.commit();

    }

    private void attachFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment2");
        ft.attach(fragment);
        ft.commit();

    }

    private void popFragment() {
        fm.popBackStack();
    }

    private void replaceFragment2() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment2 fragment2 = new Fragment2();
        ft.replace(R.id.fragmentContainer, fragment2, "fragment2");
        ft.commit();
    }

    private void removeFragment2() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment2");
        ft.remove(fragment);
        ft.commit();

    }

    private void removeFragment1() {

        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = fm.findFragmentByTag("fragment1");
        ft.remove(fragment);
        ft.addToBackStack("fragment1");
        ft.commit();
    }

    private void addFragment2() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment2 fragment2 = new Fragment2();
        ft.add(R.id.fragmentContainer, fragment2, "fragment2");
        ft.commit();
    }

    private void addFragment1() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment1 fragment1 = new Fragment1();
        ft.add(R.id.fragmentContainer, fragment1, "fragment1");
        ft.addToBackStack("fragment1");
        ft.commit();
    }
}
