package com.baidu.hi.sdk.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;

import com.baidu.hi.sdk.Constant;
import com.baidu.hi.sdk.R;
import com.baidu.hi.sdk.adapters.ChatMsgEntity;
import com.baidu.hi.sdk.adapters.ChatMsgViewAdapter;
import com.baidu.hi.sdk.utils.IntentUtil;
import com.baidu.hi.sdk.utils.MessageUtil;
import com.baidu.imc.IMPlusSDK;
import com.baidu.imc.callback.PageableResult;
import com.baidu.imc.callback.PageableResultCallback;
import com.baidu.imc.callback.ResultCallback;
import com.baidu.imc.client.IMConversation;
import com.baidu.imc.client.MessageHelper;
import com.baidu.imc.impl.im.message.BDHiIMMessage;
import com.baidu.imc.impl.im.message.BDHiIMVoiceMessage;
import com.baidu.imc.listener.IMConversationListener;
import com.baidu.imc.listener.ProgressListener;
import com.baidu.imc.message.IMMessage;
import com.baidu.imc.message.TextMessage;
import com.baidu.imc.message.content.VoiceMessageContent;
import com.baidu.imc.type.AddresseeType;
import com.baidu.imc.type.NotificationType;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConversationActivity extends Activity implements IMConversationListener {

    private static final String TAG = "ConversationActivity";
    private String conversationID;
    private String oppsiteID;
    private AddresseeType oppsiteType;
    private String currentAccount;
    private String currentID;

    private TextView tvOppsite;
    private Button btBack;
    private Button btHistory;

    private static IMConversation conversation;
    private long beforeMsgID;

    // 锟斤拷锟斤拷锟斤拷锟斤拷锟�
    private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();

    private Button mBtnVoice;
    private Button mBtnMore;
    private Button mBtnAdd;
    private Button mBtnSend;
    private EditText mEditTextContent;
    private ChatMsgViewAdapter mAdapter;
    private PullToRefreshListView mPullRefreshListView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        conversationID = getIntent().getStringExtra("CONVERSATION_ID");
        String[] results = conversationID.split(":");
        oppsiteType = AddresseeType.valueOf(results[0]);
        oppsiteID = results[1];
        currentAccount = getIntent().getStringExtra("CURRENT_ACCOUNT");
        currentID = getIntent().getStringExtra("CURRENT_ID");

        tvOppsite = (TextView) findViewById(R.id.tv_oppsite);
        tvOppsite.setText(conversationID);

        btBack = (Button) findViewById(R.id.btn_back);
        btBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btHistory = (Button) findViewById(R.id.btn_history);
        btHistory.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConversationActivity.this, HistoryActivity.class);
                intent.putExtra("CONVERSATION_ID", conversationID);
                intent.putExtra("CURRENT_ACCOUNT", currentAccount);
                intent.putExtra("CURRENT_ID", currentID);
                ConversationActivity.this.startActivity(intent);
            }
        });
        btHistory.setVisibility(View.INVISIBLE); // 隐藏"历史"按钮

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.listview);
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                ConversationActivity.this.refreshMsgs();
            }

        });
        mListView = mPullRefreshListView.getRefreshableView();
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long messageID = mDataArrays.get(position - 1).getMessageID();
                Toast.makeText(ConversationActivity.this, "" + messageID, Toast.LENGTH_SHORT).show();
                conversation.deleteMessage(messageID);
                mDataArrays.remove(position - 1);
                mAdapter.notifyDataSetChanged();
            }

        });
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                send();
            }
        });
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMore();
            }
        });
        mBtnMore = (Button) findViewById(R.id.btn_more);
        mBtnMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMore2();
            }
        });
        mBtnVoice = (Button) findViewById(R.id.btn_voice);
        mBtnVoice.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendMore3();
            }
        });

        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);

        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);

        if (conversation != null) {
            conversation.close();
        }
        conversation = IMPlusSDK.getImpClient().openIMConversation(oppsiteType, oppsiteID);
        if (conversation != null) {
            conversation.setIMConversationListener(this);
            conversation.start();
        }
        beforeMsgID = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "添加本地消息");
        menu.add(0, 1, 1, "发送语音");
        menu.add(0, 2, 2, "获取当前会话模式");
        menu.add(0, 3, 3, "设置会话模式为 MessageOnly");
        menu.add(0, 4, 4, "设置会话模式为 ReceiveNotification");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        switch (itemId) {
            case 0:
                // 增加一条随机本地消息
                MessageHelper helper = IMPlusSDK.getImpClient().getMessageHelper();
                TextMessage transientMessage =
                        helper.newTextMessage();
                transientMessage.addText(helper.newTextMessageContent("Hi, I'm random transient Message!"));
                IMPlusSDK.getImpClient().insertLocalMessage(oppsiteType, oppsiteID,
                        transientMessage, false);
                Toast.makeText(this, "Add Local Message", Toast.LENGTH_SHORT).show();
                return true;
            case 1:
                // 发送一条语音
                // Extract asset file to sdcard
                File voiceFile = new File("/sdcard/Download/voice.mp3");
                if (!voiceFile.exists()) {
                    ReadableByteChannel rc = null;
                    WritableByteChannel wc = null;
                    try {
                        voiceFile.getParentFile().mkdirs();
                        rc = Channels.newChannel(getAssets().open("voice.mp3"));
                        wc = Channels.newChannel(new FileOutputStream(voiceFile));
                        ByteBuffer buffer = ByteBuffer.allocate(4 * 1024);
                        while (rc.read(buffer) != -1) {
                            buffer.flip();
                            wc.write(buffer);
                            buffer.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (rc != null) {
                            try {
                                rc.close();
                            } catch (IOException e) {
                                // ignored
                            }
                        }
                        if (wc != null) {
                            try {
                                wc.close();
                            } catch (IOException e) {
                                // ignored
                            }
                        }
                    }
                }
                BDHiIMVoiceMessage msg =
                        (BDHiIMVoiceMessage) IMPlusSDK.getImpClient().getMessageHelper().newVoiceMessage();
                VoiceMessageContent content =
                        IMPlusSDK.getImpClient().getMessageHelper().newVoiceMessageContent();
                content.setFileUploadListener(new ProgressListener<String>() {
                    @Override
                    public void onProgress(float v) {
                        Log.i(TAG, "file upload progress: " + v);
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.i(TAG, "file upload error: " + i + " reason: " + s);
                    }

                    @Override
                    public void onSuccess(String s) {
                        Log.i(TAG, "file upload success: " + s);
                    }
                });
                content.setFileName(voiceFile.getName());
                content.setFilePath(voiceFile.getPath());
                content.setDuration(10);

                msg.setVoice(content);
                msg.setAddresserID(currentID);
                msg.setAddresserName(currentAccount);
                msg.setAddresseeID(oppsiteID);
                msg.setAddresseeType(oppsiteType);

                msg.setCompatibleText("收到一条语音消息。");
                msg.setNotificationText("收到一条语音消息。");
                conversation.sendMessage(msg);
                return true;
            case 2: {
                // 获取当前会话模式
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final NotificationType notificationSetting =
                                IMPlusSDK.getImpClient().getNotificationSetting(oppsiteType, oppsiteID);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ConversationActivity.this, "获取成功, 当前会话模式为 " + notificationSetting,
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }
                }).start();
                return true;
            }
            case 3: {
                // 设置会话模式为 MessageOnly
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IMPlusSDK.getImpClient().setNotificationSetting(oppsiteType, oppsiteID,
                                NotificationType.RECEIVE_MESSAGE_ONLY, new ResultCallback<Boolean>() {
                                    @Override
                                    public void result(final Boolean result, Throwable throwable) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ConversationActivity.this,
                                                        "操作 " + (result ? "成功" : "失败"),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                });
                    }
                }).start();
                return true;
            }
            case 4: {
                // 设置会话模式为 ReceiveNotification
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        IMPlusSDK.getImpClient().setNotificationSetting(oppsiteType, oppsiteID,
                                NotificationType.RECEIVE_NOTIFICATION, new ResultCallback<Boolean>() {
                                    @Override
                                    public void result(final Boolean result, Throwable throwable) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(ConversationActivity.this,
                                                        "操作 " + (result ? "成功" : "失败"),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                });
                    }
                }).start();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != conversation) {
            conversation.active();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (conversation != null) {
            conversation.deactive();
            //conversation.close();
        }
    }

    @Override
    public void onDestroy() {
        if (conversation != null) {
            conversation.deactive();
            conversation.close();
        }
        super.onDestroy();
    }

    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (TextUtils.isEmpty(contString)) {
            return;
        }
        MessageUtil.sendTextMessage(this, contString, currentAccount, conversation);

        ConversationActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mEditTextContent.setText("");
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mListView.getCount() - 1);
            }
        });
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditTextContent.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void sendMore() {
        IntentUtil.openImageAlbums(this);
    }

    private void sendMore2() {
        IntentUtil.openImageAlbums2(this);
    }

    private void sendMore3() {
        IntentUtil.openImageAlbums3(this);
    }

    @Override
    public void onMessageChanged(final IMMessage message, List<String> arg1) {
        if (null != message) {
            ConversationActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    MessageUtil.updateMessage(mDataArrays, message);
                    Toast.makeText(ConversationActivity.this,
                            "SendMessage:" + message.getMessageID() + " status:" + message.getStatus(),
                            Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onNewMessageReceived(final IMMessage message) {
        if (null != message) {
            // if (message instanceof IMVoiceMessage) {
            // System.out.println("onNewMessageReceived: filePath" + ((IMVoiceMessage) message).getVoice().toString());
            // }
            if (beforeMsgID == 0) {
                beforeMsgID = message.getMessageID();
            }
            ConversationActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    MessageUtil.receiveMessage(mDataArrays, message);
                    Toast.makeText(ConversationActivity.this,
                            "ReceiveMessage:" + message.getMessageID() + " status:" + message.getStatus(),
                            Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(mListView.getCount() - 1);
                }

            });
        }
    }

    private void refreshMsgs() {
        if (conversation == null) {
            return;
        }
        // beforeMsgSeq -= 1;
        conversation.getMessageList(beforeMsgID, 10, 2000, new PageableResultCallback<IMMessage>() {

            @Override
            public void result(final PageableResult<IMMessage> result, final Throwable error) {
                new Thread() {

                    @Override
                    public void run() {
                        if (error == null && result != null && null != result.getList() && !result.getList()
                                .isEmpty()) {
                            beforeMsgID = result.getList().get(0).getMessageID();
                            final List<ChatMsgEntity> tmpDatas = new ArrayList<ChatMsgEntity>();
                            for (int i = 0; i < result.getList().size(); i++) {
                                IMMessage message = (BDHiIMMessage) result.getList().get(i);
                                tmpDatas.add(MessageUtil.getChatMsgEntity((BDHiIMMessage) message));
                            }
                            final int beforePosition = tmpDatas.size();
                            ConversationActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    mDataArrays.addAll(0, tmpDatas);
                                    mAdapter.notifyDataSetChanged();
                                    mPullRefreshListView.onRefreshComplete();
                                    mListView.setSelection(beforePosition);
                                }

                            });
                        } else {
                            ConversationActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    mPullRefreshListView.onRefreshComplete();
                                }

                            });
                        }
                    }

                }.start();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            ConversationActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(ConversationActivity.this, "无法获取相片1", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        switch (requestCode) {
            case Constant.PHOTO_PICKED_WITH_DATA: // 调用Gallery返回的
                MessageUtil.sendImageMessage(this, data.getData(), currentAccount, conversation);
                break;
            case Constant.PHOTO_PICKED_WITH_DATA2:
                MessageUtil.sendCustomMessage(this, data.getData(), "这是一条额外的文字消息", "这是另外额外的一条文字消息", currentAccount,
                        conversation);
                break;
            case Constant.PHOTO_PICKED_WITH_DATA3:
                MessageUtil.sendVoiceMessage(this, data.getData(), currentAccount, conversation);
                break;
            default:
                break;
        }
    }
}
