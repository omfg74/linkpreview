package ru.omfgdevelop.linkpreview.view;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.ViewTarget;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.omfgdevelop.linkpreview.R;
import ru.omfgdevelop.linkpreview.LinkPreview;
import ru.omfgdevelop.linkpreview.interfaces.AdapterInterface;
import ru.omfgdevelop.linkpreview.interfaces.PictureLoaderInterface;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder> implements AdapterInterface {
    List<PreviewObject> previewObjects = new ArrayList<>();
    PictureLoaderInterface pictureLoaderInterface;

    private ViewTarget<ImageView, Bitmap> requestBuilder;
    @NonNull

    @Override
    public int getItemViewType(int position) {
        PreviewObject previewObject = previewObjects.get(position);
        if (previewObject != null) {
            return previewObject.getType();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;

        switch (i) {
            case 1:
            view = LayoutInflater.from(LinkPreview.getContext()).inflate(R.layout.item_linkprewiew_item, viewGroup, false);
            return new LinkViewHolder(view);
            case 2:
                view =  LayoutInflater.from(LinkPreview.getContext()).inflate(R.layout.item_simplemessage, viewGroup, false);
                return new SimpleViewHoldr(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int i) {
        switch (previewObjects.get(i).getType()) {
            case 1:
//           textTextView.setText(previewObjects.get(i).getText());
//            if (previewObjects.get(i).getUrl() != null) {
//                titleTextView.setText(previewObjects.get(i).getTitle());
//               descriptionTextView.setText(previewObjects.get(i).getDescription());
//                String url = previewObjects.get(i).getImage();
//                Log.d("Log", "url "+url);
//                if (url!=""){
//                    try{
//                Picasso.with(LinkPreview.getContext()).load(url).fit()
//                        .centerInside().into(imageImageView);
//                }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
                viewHolder.bind(previewObjects.get(i));
            break;
            case 2:
//                simpleTextView.setText(previewObjects.get(i).getText());
                viewHolder.bind(previewObjects.get(i));
//       Glide.with(LinkPreview.getContext())
//               .asBitmap()
//               .placeholder(R.drawable.ic_launcher_background)
//               .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .load(url)
//                .into(viewHolder.imageImageView);
        }

    }
    @Override
    public int getItemCount() {
        return previewObjects.size();
    }

    @Override
    public void appendData(PreviewObject previewObject) {
        previewObjects.add(previewObject);
        notifyDataSetChanged();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void bind(PreviewObject previewObject){

        }
    }
   final public class LinkViewHolder extends BaseViewHolder {
       TextView simpleTextView, textTextView, titleTextView, descriptionTextView;
       ImageView imageImageView;
        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            setIsRecyclable(false);
            titleTextView= itemView.findViewById(R.id.titleTextView);
            descriptionTextView= itemView.findViewById(R.id.bodyTextView);
            textTextView= itemView.findViewById(R.id.textTextView);
            imageImageView= itemView.findViewById(R.id.previewImageView);

        }

       @Override
       public void bind(PreviewObject previewObject) {
           titleTextView.setText(previewObject.getTitle());
           descriptionTextView.setText(previewObject.getDescription());
           titleTextView.setText(previewObject.getText());
           String url = previewObject.getImage();
           Log.d("Log", "url "+url);
           if (url!=""){
               try{
                   Picasso.with(LinkPreview.getContext()).load(url).fit()
                           .centerInside().into(imageImageView);
               }catch (Exception e){
                   e.printStackTrace();
               }
           }

       }
   }
    final public class SimpleViewHoldr extends BaseViewHolder{
        TextView simpleTextView;
        public SimpleViewHoldr(@NonNull View itemView) {
            super(itemView);
            setIsRecyclable(false);
            simpleTextView= itemView.findViewById(R.id.textTextView);
        }

        @Override
        public void bind(PreviewObject previewObject) {
            simpleTextView.setText(previewObject.getText());
        }
    }
}
