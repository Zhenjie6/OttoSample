package org.study.ottosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mClearButton;
    private Button mMoveButton;

    private float DEFAULT_LONGITUDE = 116.413554f;
    private float DEFAULT_LATITUDE = 39.911013f;

    private float longitude;
    private float latitude;

    private float OFFSET = 0.1f;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClearButton = (Button)findViewById(R.id.bt_clear_location);
        mMoveButton = (Button)findViewById(R.id.bt_move_location);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new LocationClearEvent());
            }
        });
        mMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longitude = DEFAULT_LONGITUDE + OFFSET * random.nextFloat();
                latitude = DEFAULT_LATITUDE + OFFSET * random.nextFloat();
                BusProvider.getInstance().post(new LocationMoveEvent(longitude, latitude));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }
}
