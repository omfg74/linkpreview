package ru.omfgdevelop.linkpreview.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.omfgdevelop.linkpreview.R;
import ru.omfgdevelop.linkpreview.LinkPreview;
import ru.omfgdevelop.linkpreview.interfaces.AdapterInterface;
import ru.omfgdevelop.linkpreview.interfaces.AdapterItemNumberCallBack;
import ru.omfgdevelop.linkpreview.repository.Constants;
import ru.omfgdevelop.linkpreview.repository.PreviewObject;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.BaseViewHolder> implements AdapterInterface {
    List<PreviewObject> previewObjects = new ArrayList<>();
    AdapterItemNumberCallBack callBack;

    public RecyclerViewAdapter(AdapterItemNumberCallBack callBack) {
        this.callBack = callBack;
    }

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
        View view;

        switch (i) {
            case Constants.SNIPPETMESSAGE:
                view = LayoutInflater.from(LinkPreview.getContext()).inflate(R.layout.item_linkprewiew_item, viewGroup, false);
                return new LinkViewHolder(view);
            case Constants.SIMPLE_MESSAGE:
                view = LayoutInflater.from(LinkPreview.getContext()).inflate(R.layout.item_simplemessage, viewGroup, false);
                return new SimpleViewHoldr(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int i) {
//                        viewHolder.bind(previewObjects.get(i), i);//
        switch (previewObjects.get(i).getType()){
            case Constants.SNIPPETMESSAGE:
                    viewHolder.bind(previewObjects.get(i), i);
                    break;
                case Constants.SIMPLE_MESSAGE:
                    viewHolder.bind(previewObjects.get(i));
                    break;
                    default:
                        break;
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
    public void removeItem(int item){
        previewObjects.remove(item);

    }
    public void addDataToItem(int itemNumber, PreviewObject previewObject){
        this.previewObjects.set(itemNumber,previewObject);
        notifyDataSetChanged();
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(PreviewObject previewObject, int i) {

            Log.d("SUPER","call parent");
        }
        public void bind(PreviewObject previewObject) {
            Log.d("SUPER","call parent");
        }

    }

    final public class LinkViewHolder extends BaseViewHolder {
        TextView  textTextView, titleTextView, descriptionTextView;
        ImageView imageImageView;

        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.bodyTextView);
            textTextView = itemView.findViewById(R.id.textTextView);
            imageImageView = itemView.findViewById(R.id.previewImageView);

        }

        @Override
        public void bind(@NonNull PreviewObject previewObject, int i) {
            textTextView.setText(previewObject.getText());
            if (previewObject.getImage()!=null){
            titleTextView.setText(previewObject.getTitle());
            descriptionTextView.setText(previewObject.getDescription());
            String url = previewObject.getImage();
            Log.d("Log", "url " + url);
            if (url != "") {
                try {
                    Picasso.with(LinkPreview.getContext()).load(url).fit()
                            .centerInside().into(imageImageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else callBack.giveItemNumber(i, previewObject);
        }
    }

    final public class SimpleViewHoldr extends BaseViewHolder {
        TextView simpleTextView;

        public SimpleViewHoldr(@NonNull View itemView) {
            super(itemView);
            simpleTextView = itemView.findViewById(R.id.textTextView);
        }

        @Override
        public void bind(@NonNull PreviewObject previewObject) {
            simpleTextView.setText(previewObject.getText());
        }
    }

}
