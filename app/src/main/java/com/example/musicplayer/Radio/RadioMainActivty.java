package com.example.musicplayer.Radio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.musicplayer.R;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by mokshaDev on 4/19/2015.
 */
public class RadioMainActivty extends Activity implements View.OnClickListener {
    ButtonRectangle localRadio;
    ButtonRectangle countryRadio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio_main_layout);
        localRadio = (ButtonRectangle) findViewById(R.id.btnLocalRadio);
        countryRadio = (ButtonRectangle) findViewById(R.id.btnCountryRadio);
        localRadio.setOnClickListener(this);
        countryRadio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent localr = new Intent(this,ChannelCollection.class);
        Intent countryr = new Intent(this, CountryRadioMainActivity.class);
        if(v.getId()==R.id.btnLocalRadio)
        {
            startActivity(localr);
        }
        if(v.getId()==R.id.btnCountryRadio)
        {
            startActivity(countryr);
        }
    }
}
