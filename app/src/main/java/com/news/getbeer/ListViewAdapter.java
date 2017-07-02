package com.news.getbeer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import com.squareup.picasso.Picasso;




public class ListViewAdapter extends BaseAdapter {


	Context mContext;
	LayoutInflater inflater;
	private ArrayList<GetBeer> gb1 = null;
	private ArrayList<GetBeer> gb2;

	ImageView urlToImage;

public ListViewAdapter(Context context, ArrayList<GetBeer> worldpopulationlist) {
	mContext = context;
	this.gb1 = worldpopulationlist;
	inflater = LayoutInflater.from(mContext);
	this.gb2 = new ArrayList<GetBeer>();
	this.gb2.addAll(gb1);
}

	@Override
	public int getCount() {
        return gb1.size();
	}

	@Override
	public GetBeer getItem(int position) {
		return gb1.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		TextView name;
		TextView description;
		TextView firstbrewed;

		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	View itemView = inflater.inflate(R.layout.listview_item, parent, false);

		// Locate the TextViews in listview_item.xml
		name = (TextView) itemView.findViewById(R.id.title);
		description = (TextView) itemView.findViewById(R.id.description);
		firstbrewed = (TextView) itemView.findViewById(R.id.firstbrewed);
		description.setMovementMethod(new ScrollingMovementMethod());
		// Locate the ImageView in listview_item.xml
	urlToImage = (ImageView) itemView.findViewById(R.id.urlToImage);


		name.setText(gb1.get(position).getName());
		description.setText(gb1.get(position).getDescription());
		firstbrewed.setText(gb1.get(position).getFirstBrewed());

		View.OnTouchListener listener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean isLarger;

				isLarger = ((TextView) v).getLineCount()
						* ((TextView) v).getLineHeight() > v.getHeight();
				if (event.getAction() == MotionEvent.ACTION_MOVE
						&& isLarger) {
					v.getParent().requestDisallowInterceptTouchEvent(true);

				} else {
					v.getParent().requestDisallowInterceptTouchEvent(false);

				}
				return false;
			}
		};

		description.setOnTouchListener(listener);

		//used picasso for images
		Picasso.with(mContext)
				.load(gb1.get(position).getUrl())
			//	.networkPolicy(NetworkPolicy.OFFLINE)
				.placeholder(R.drawable.temp_img)
				.error(R.drawable.temp_img)
				.into(urlToImage);
//				.into(urlToImage, new Callback() {
//			@Override
//			public void onSuccess() {
//
//			}
//
//			@Override
//			public void onError() {
//				//Try again online if cache failed
//				Picasso.with(context)
//						.load(gb1.get(position).getUrl())
//						.error(R.drawable.temp_img)
//						.placeholder(R.drawable.temp_img)
//						.into(urlToImage);
//			}
//		});
		itemView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Uri uri = Uri.parse((gb1.get(position).getUrl()));
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				mContext.startActivity(intent);

			}
		});
		return itemView;
	}
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		gb1.clear();
		if (charText.length() == 0) {
			gb1.addAll(gb2);
		}
		else
		{
			for (GetBeer wp : gb2)
			{
				if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText))
				{
					gb1.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}

