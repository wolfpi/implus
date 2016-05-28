package com.baidu.hi.sdk.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.hi.sdk.R;
import com.baidu.hi.sdk.activities.ImageDetailActivity;
import com.baidu.hi.sdk.utils.ImageLoader;
import com.baidu.imc.impl.im.message.content.BDHiVoiceMessageContent;
import com.baidu.imc.message.content.VoiceMessageContent;

public class ChatMsgViewAdapter extends BaseAdapter {

    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;
        int IMVT_TO_MSG = 1;
        int IMVT_COM_IMG = 2;
        int IMVT_TO_IMG = 3;
        int IMVT_COM_CUS = 4;
        int IMVT_TO_CUS = 5;
        int IMVT_COM_VOI = 6;
        int IMVT_TO_VOI = 7;
    }

    private List<ChatMsgEntity> data;
    private Context context;
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsgEntity entity = data.get(position);

        if (entity.getMsgType()) {
            if ("image".equalsIgnoreCase(entity.getDisplayMsgType())) {
                return IMsgViewType.IMVT_COM_IMG;
            } else if ("custom".equalsIgnoreCase(entity.getDisplayMsgType())) {
                return IMsgViewType.IMVT_COM_CUS;
            } else if ("voice".equalsIgnoreCase(entity.getDisplayMsgType())) {
                return IMsgViewType.IMVT_COM_VOI;
            } else {
                return IMsgViewType.IMVT_COM_MSG;
            }
        } else {
            if ("image".equalsIgnoreCase(entity.getDisplayMsgType())) {
                return IMsgViewType.IMVT_TO_IMG;
            } else if ("custom".equalsIgnoreCase(entity.getDisplayMsgType())) {
                return IMsgViewType.IMVT_TO_CUS;
            } else if ("voice".equalsIgnoreCase(entity.getDisplayMsgType())) {
                return IMsgViewType.IMVT_TO_VOI;
            } else {
                return IMsgViewType.IMVT_TO_MSG;
            }
        }

    }

    @Override
    public int getViewTypeCount() {
        return 8;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ChatMsgEntity entity = data.get(position);
        // boolean isComMsg = entity.getMsgType();

        ViewHolder viewHolder = null;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            switch (getItemViewType(position)) {
                case IMsgViewType.IMVT_COM_MSG:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                case IMsgViewType.IMVT_TO_MSG:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                case IMsgViewType.IMVT_COM_IMG:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_image_left, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.tv_chatimage);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                case IMsgViewType.IMVT_TO_IMG:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_image_right, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.tv_chatimage);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                case IMsgViewType.IMVT_COM_CUS:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_custom_left, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.tv_chatimage);
                    viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                case IMsgViewType.IMVT_TO_CUS:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_custom_right, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.tv_chatimage);
                    viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                case IMsgViewType.IMVT_COM_VOI:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_custom_left, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.tv_chatimage);
                    viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                case IMsgViewType.IMVT_TO_VOI:
                    convertView = mInflater.inflate(R.layout.chatting_item_msg_custom_right, null);
                    viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
                    viewHolder.tvSendStatus = (TextView) convertView.findViewById(R.id.tv_sendstatus);
                    viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
                    viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.tv_chatimage);
                    viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                    viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_sendid);
                    break;
                default:
                    break;
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (getItemViewType(position)) {
            case IMsgViewType.IMVT_COM_MSG:
                viewHolder.tvSendTime.setText(entity.getDate());
                viewHolder.tvSendStatus.setText(entity.getStatus());
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.tvContent.setText(entity.getText());
                viewHolder.tvID.setText(entity.getId());
                break;
            case IMsgViewType.IMVT_TO_MSG:
                viewHolder.tvSendTime.setText(entity.getDate());
                viewHolder.tvSendStatus.setText(entity.getStatus());
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.tvContent.setText(entity.getText());
                viewHolder.tvID.setText(entity.getId());
                break;
            case IMsgViewType.IMVT_COM_IMG:
                viewHolder.tvSendTime.setText(entity.getDate());
                viewHolder.tvSendStatus.setText(entity.getStatus());
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.ivImage.setImageResource(R.drawable.logo);
                ImageLoader.getInstance().displayThumbnail(entity.getFileName(), viewHolder.ivImage,
                        entity.getFileMessageContent());
                viewHolder.ivImage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ImageDetailActivity.class);
                        intent.putExtra("FILE_NAME", entity.getBigFileName());
                        ImageDetailActivity.fileMessageContent = entity.getBigFileMessageContent();
                        context.startActivity(intent);
                    }
                });
                viewHolder.tvID.setText(entity.getId());
                break;
            case IMsgViewType.IMVT_TO_IMG:
                viewHolder.tvSendTime.setText(entity.getDate());
                viewHolder.tvSendStatus.setText(entity.getStatus());
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.ivImage.setImageResource(R.drawable.logo);
                ImageLoader.getInstance().displayThumbnail(entity.getFileName(), viewHolder.ivImage,
                        entity.getFileMessageContent());
                viewHolder.ivImage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ImageDetailActivity.class);
                        intent.putExtra("FILE_NAME", entity.getBigFileName());
                        ImageDetailActivity.fileMessageContent = entity.getBigFileMessageContent();
                        context.startActivity(intent);
                    }
                });
                viewHolder.tvID.setText(entity.getId());
                break;
            case IMsgViewType.IMVT_COM_CUS:
                viewHolder.tvSendTime.setText(entity.getDate());
                viewHolder.tvSendStatus.setText(entity.getStatus());
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.ivImage.setImageResource(R.drawable.logo);
                ImageLoader.getInstance().displayThumbnail(entity.getFileName(), viewHolder.ivImage,
                        entity.getFileMessageContent());
                viewHolder.ivImage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ImageDetailActivity.class);
                        intent.putExtra("FILE_NAME", entity.getBigFileName());
                        ImageDetailActivity.fileMessageContent = entity.getBigFileMessageContent();
                        context.startActivity(intent);
                    }
                });
                viewHolder.tvID.setText(entity.getId());
                viewHolder.tvContent.setText(entity.getText());
                break;
            case IMsgViewType.IMVT_TO_CUS:
                viewHolder.tvSendTime.setText(entity.getDate());
                viewHolder.tvSendStatus.setText(entity.getStatus());
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.ivImage.setImageResource(R.drawable.logo);
                ImageLoader.getInstance().displayThumbnail(entity.getFileName(), viewHolder.ivImage,
                        entity.getFileMessageContent());
                viewHolder.ivImage.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ImageDetailActivity.class);
                        intent.putExtra("FILE_NAME", entity.getBigFileName());
                        ImageDetailActivity.fileMessageContent = entity.getBigFileMessageContent();
                        context.startActivity(intent);
                    }
                });
                viewHolder.tvID.setText(entity.getId());
                viewHolder.tvContent.setText(entity.getText());
                break;
            case IMsgViewType.IMVT_COM_VOI: {
                final VoiceMessageContent voiceContent = (VoiceMessageContent) entity.getFileMessageContent();
                viewHolder.tvSendTime.setText(entity.getDate());
                String status = entity.getStatus();
                status += voiceContent.isPlayed() ? "(played)" : "(unplayed)";
                viewHolder.tvSendStatus.setText(status);
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.ivImage.setImageResource(R.drawable.ofm_camera_icon);
                ImageLoader.getInstance().displayThumbnail(entity.getFileName(), viewHolder.ivImage,
                        entity.getFileMessageContent());
                viewHolder.ivImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "语音设置为已读", Toast.LENGTH_LONG).show();
                        voiceContent.setPlayed(true);
                    }
                });
                viewHolder.tvID.setText(entity.getId());
                viewHolder.tvContent.setText(entity.getText());
            }
                break;
            case IMsgViewType.IMVT_TO_VOI: {
                final VoiceMessageContent voiceContent = (VoiceMessageContent) entity.getFileMessageContent();
                viewHolder.tvSendTime.setText(entity.getDate());
                String status = entity.getStatus();
                status += voiceContent.isPlayed() ? "(played)" : "(unplayed)";
                viewHolder.tvSendStatus.setText(status);
                viewHolder.tvUserName.setText(entity.getName());
                viewHolder.ivImage.setImageResource(R.drawable.ofm_camera_icon);
                ImageLoader.getInstance().displayThumbnail(entity.getFileName(), viewHolder.ivImage,
                        entity.getFileMessageContent());
                viewHolder.tvID.setText(entity.getId());
                viewHolder.tvContent.setText(entity.getText());
            }
                break;
            default:
                break;
        }

        return convertView;
    }

    // 閫氳繃ViewHolder鏄剧ず椤圭殑鍐呭
    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvSendStatus;
        public TextView tvUserName;
        public TextView tvContent;
        public TextView tvID;
        public ImageView ivImage;
    }

}