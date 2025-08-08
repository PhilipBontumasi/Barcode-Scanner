package com.example.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, ItemListFragment.ItemListFragmentListener, RegisterFragment.RegisterFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void replaceWithUserFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ItemListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceWithRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, RegisterFragment.newInstance())
                .commit();
    }

    @Override
    public void replaceWithCreatorFragment() {

    }
    @Override
    public void replaceWithItemDetailsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ItemListFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void replaceWithScanItemFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ScanItemFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void backFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

}