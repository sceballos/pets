package net.project.pets.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.project.pets.R;
import net.project.pets.data.Pet;
import net.project.pets.ui.PetDetailsActivity;
import net.project.pets.util.BitmapUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    private ArrayList<Pet> mData;
    private LayoutInflater mInflater;

    public PetAdapter(Context context, ArrayList<Pet> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.pet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pet petData = mData.get(position);
        new loadImageTask(holder.petImage, petData.getImageUrl()).execute("");
        holder.petName.setText(petData.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView petImage;
        TextView petName;

        ViewHolder(View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.pet_image);
            petName = itemView.findViewById(R.id.pet_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pet clickedPet = mData.get(getAdapterPosition());
                    startDetailsActivity(v.getContext(), clickedPet);
                }
            });
        }
    }

    private class loadImageTask extends AsyncTask<String, Void, String> {

        private ImageView imageView;
        private String imageURl = "";
        private Bitmap imageBitmap = null;


        public loadImageTask(ImageView imageView, String imageURl){
            this.imageView = imageView;
            this.imageURl = imageURl;
        }

        @Override
        protected String doInBackground(String... params) {
            URL imageUrl = null;
            try {
                imageUrl = new URL(imageURl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {

                Bitmap tempImageBitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                Bitmap thumbnail = BitmapUtil.makeThumbnail(tempImageBitmap);
                imageBitmap = BitmapUtil.getResizedBitmap(thumbnail, 500);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            imageView.setImageBitmap(this.imageBitmap);
        }
    }

    private void startDetailsActivity(Context context, Pet pet) {
        Intent intent = new Intent(context, PetDetailsActivity.class);
        intent.putExtra("pet_info", pet);
        context.startActivity(intent);
    }

    Pet getItem(int id) {
        return mData.get(id);
    }
}
