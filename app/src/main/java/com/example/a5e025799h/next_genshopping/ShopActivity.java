package com.example.a5e025799h.next_genshopping;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    EditText Shop;
    ListView listView;

    String[] theTitles;
    String[] theDesc;
    int[] images = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10};

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        listView = (ListView) findViewById(R.id.listView);

        Resources r = getResources();
        theTitles = r.getStringArray(R.array.titles);
        theDesc = r.getStringArray(R.array.Desc);
        System.out.println(theTitles);
        System.out.println(theDesc);
        MyAdapter adapter = new MyAdapter(this, theTitles, images, theDesc);
        listView.setAdapter(adapter);

        auth = FirebaseAuth.getInstance();

        Shop = (EditText)findViewById(R.id.Shop);

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        int[] imgs;
        String[] titleArray;
        String[] descArray;

        public MyAdapter(Context c, String[] titles, int imgs[], String[] desc) {
            super(c, R.layout.single_row1, R.id.textView, titles);
            this.context = c;
            this.imgs = imgs;
            this.titleArray = titles;
            this.descArray = desc;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.single_row1, parent, false);

            ImageView myImage = (ImageView) row.findViewById(R.id.imageView);
            TextView myTitle = (TextView) row.findViewById(R.id.textView);
            TextView myDesc = (TextView) row.findViewById(R.id.textView2);

            myImage.setImageResource(imgs[position]);
            myTitle.setText(titleArray[position]);
            myDesc.setText(descArray[position]);

            return row;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logoutMenu:
                logoutUser();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser(){
        auth.signOut();
        if(auth.getCurrentUser()==null){
            startActivity(new Intent(ShopActivity.this, MainActivity.class));
            finish();
        }

    }
    public void Order(View view){
        if(view.getId()==R.id.Order){
            Intent intent = new Intent(this, ScheduleActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("data", (Serializable) Shop.getText().toString());
            intent.putExtra("bundle", args);
            startActivity(intent);
        }
    }


}
