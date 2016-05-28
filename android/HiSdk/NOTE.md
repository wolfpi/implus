# 开发笔记


### 语音消息已读未读标记，ios端方案同步一下：

  在VoiceContent上增加接口：

      @property (nonatomic, readonly, assign) BOOL played; // 语音消息是否已播放

      - (void)markAsPlayed; // 标记为已播放

  使用会话的最后已读消息lastReadMessageID作为对比，
  收到的或同步下来的语音消息，如果serverMessageID > lastReadMessageID, 则将语音消息标记为未读，此标记和消息一起入库；
  如果serverMessageID <= lastReadMessageID，则将语音消息标记为已读，此标记和消息一起入库。

  因为自定义消息里面也可能有语音消息，所以一条消息可能有多条语音。ios的消息数据库有一个extra字段，在extra里存储已读未读标记 @{voiceId1：0，voiceId2:1，….}




### 我在做地图的新需求 app插入本地消息的功能

  发现数据库需要有字段记录这条消息是否可以作为会话的最后一条，因为有些消息，比如系统消息，不想显示在会话列表上。iOS可能需要数据库升级 增加字段，不知道android是否需要，做这个功能的时候关注一下




### 关于 Protobuf micro 迁移

可以用以下正则进行替换

    \.Builder(.*)(\s+)(\S+)\.newBuilder\(\);
    $1 new $3();

    \.builder\(\)



    (\w+)\.toByteStringMicro\(\)
    ByteStringMicro.copyFrom($1.toByteArray())