package com.news.getbeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class SingleItemView extends Activity {
	// Declare Variables
	String name;
	String description;
	String firstbrewed;
	String imageurl;

//

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.listview_item);

		Intent i = getIntent();

		name = i.getStringExtra("name");
		description = i.getStringExtra("description");
		firstbrewed = i.getStringExtra("firstbrewed");
		imageurl = i.getStringExtra("imageurl");

		// Locate the TextViews in listview_item.xml
		TextView txtname = (TextView) findViewById(R.id.title);
		TextView txtdescription = (TextView) findViewById(R.id.description);
		TextView txtfirstbrewed = (TextView) findViewById(R.id.firstbrewed);
		ImageView imgurlToImage = (ImageView) findViewById(R.id.urlToImage);

		// Set results to the TextViews
		txtname.setText(name);
		txtdescription.setText(description);
		txtfirstbrewed.setText(firstbrewed);


	}
}